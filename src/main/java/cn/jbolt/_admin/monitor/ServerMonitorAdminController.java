package cn.jbolt._admin.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.OshiUtil;
import cn.jbolt._admin.permission.CheckPermission;
import cn.jbolt._admin.permission.PermissionKey;
import cn.jbolt._admin.permission.UnCheckIfSystemAdmin;
import cn.jbolt.base.JBoltBaseController;
import cn.jbolt.common.util.DateUtil;
import cn.jbolt.common.util.IpUtil;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;
/**
 * 服务器监控
 * @ClassName:  ServerMonitorAdminController   
 * @author: JFinal学院-小木 QQ：909854136 
 * @date:   2020年5月2日   
 */
@CheckPermission(PermissionKey.JBOLT_SERVER_MONITOR)
@UnCheckIfSystemAdmin
public class ServerMonitorAdminController extends JBoltBaseController {
	
	public void index() {
		//服务器
		setServer();
		//JVM
		setJvm();
		//CPU
		setCpu();
		//内存
		setMemory();
		//磁盘
		setDisk();
		render("index.html");
	}
	
	private void setDisk() {
		 FileSystem fileSystem = OshiUtil.getOs().getFileSystem();
	     OSFileStore[] fsArray = fileSystem.getFileStores();
	     List<Kv> disks=new ArrayList<Kv>();
	     Kv kv;
	     for(OSFileStore fs:fsArray) {
	    	 kv=Kv.create();
	    	 kv.set("name",fs.getName());
	         kv.set("label",fs.getLabel());
	         kv.set("dirName",fs.getMount());
	         kv.set("sysTypeName",fs.getType());
	         kv.set("total",FileUtil.readableFileSize(fs.getTotalSpace()));
	         kv.set("free",FileUtil.readableFileSize(fs.getFreeSpace()));
	         kv.set("usable",FileUtil.readableFileSize(fs.getUsableSpace()));
	         kv.set("used",FileUtil.readableFileSize(fs.getTotalSpace()-fs.getUsableSpace()));
	         if((fs.getTotalSpace()-fs.getUsableSpace())==0) {
	        	 kv.set("usedRate","100%");
	         }else {
	        	 kv.set("usedRate",NumberUtil.mul(NumberUtil.toBigDecimal(fs.getTotalSpace()-fs.getUsableSpace()).divide(NumberUtil.toBigDecimal(fs.getTotalSpace()),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
	         }
	         disks.add(kv);
	     }
	     set("disks", disks);
	}
	
	 

	private void setMemory() {
		GlobalMemory memory = OshiUtil.getMemory();
		long total=memory.getTotal();
		long free=memory.getAvailable();
		long uesd=total-free;
		set("memoryTotal", NumberUtil.div(NumberUtil.toBigDecimal(total),NumberUtil.toBigDecimal(1024 * 1024 * 1024), 2));
		set("memoryUsed", NumberUtil.div(NumberUtil.toBigDecimal(uesd),NumberUtil.toBigDecimal(1024 * 1024 * 1024), 2));
		set("memoryFree", NumberUtil.div(NumberUtil.toBigDecimal(free),NumberUtil.toBigDecimal(1024 * 1024 * 1024), 2));
		if(uesd==0) {
			set("memoryRate","0%");
		}else {
			set("memoryRate", NumberUtil.mul(NumberUtil.toBigDecimal(uesd).divide(NumberUtil.toBigDecimal(total),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
		}
		long jvmTotal=Runtime.getRuntime().totalMemory();
		long jvmFree=Runtime.getRuntime().freeMemory();
		long jvmUsed=jvmTotal-jvmFree;
		set("jvmMemoryTotal",  NumberUtil.div(NumberUtil.toBigDecimal(jvmTotal),NumberUtil.toBigDecimal(1024 * 1024), 2));
		set("jvmMemoryUsed",  NumberUtil.div(NumberUtil.toBigDecimal(jvmUsed),NumberUtil.toBigDecimal(1024 * 1024), 2));
		set("jvmMemoryFree",  NumberUtil.div(NumberUtil.toBigDecimal(jvmFree),NumberUtil.toBigDecimal(1024 * 1024), 2));
		if(jvmUsed==0) {
			set("jvmMemoryRate","0%");
		}else {
			set("jvmMemoryRate", NumberUtil.mul(NumberUtil.toBigDecimal(jvmUsed).divide(NumberUtil.toBigDecimal(jvmTotal),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
		}
	}

	private void setServer() {
		OperatingSystem os = OshiUtil.getOs();
		set("serverHostName", os.getNetworkParams().getDomainName());
		set("serverOsName", os.toString());
		set("serverOsArch", System.getProperty("os.arch"));
		set("serverIp",IpUtil.getServerIp());
		set("projectPath", System.getProperty("user.dir"));
	}

	private void setJvm() {
		RuntimeMXBean runtimeMXBean=ManagementFactory.getRuntimeMXBean();
		set("jvmName", runtimeMXBean.getVmName());
		set("jvmVersion", runtimeMXBean.getVmVersion());
		set("jvmHome",   System.getProperty("java.home"));
		Date startTime=new Date(runtimeMXBean.getStartTime());
		set("jvmStarttime", DateUtil.format(startTime, DateUtil.YMDHMSS));
		set("jvmRunUsedTime", DateUtil.getUsedTimeStr(startTime));
	}

	private void setCpu() {
		CentralProcessor cpu=OshiUtil.getProcessor();
		set("cpuName", cpu.getName());
		set("cpuCount", cpu.getPhysicalPackageCount()+"个 "+cpu.getPhysicalProcessorCount()+"核 "+cpu.getLogicalProcessorCount()+"线程");
		// CPU信息
        long[] prevTicks = cpu.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = cpu.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        set("cpuSystemUsed", NumberUtil.mul(NumberUtil.toBigDecimal(cSys).divide(NumberUtil.toBigDecimal(totalCpu),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
        set("cpuUserUsed", NumberUtil.mul(NumberUtil.toBigDecimal(user).divide(NumberUtil.toBigDecimal(totalCpu),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
        set("cpuWait", NumberUtil.mul(NumberUtil.toBigDecimal(iowait).divide(NumberUtil.toBigDecimal(totalCpu),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
        set("cpuFree", NumberUtil.mul(NumberUtil.toBigDecimal(idle).divide(NumberUtil.toBigDecimal(totalCpu),2,BigDecimal.ROUND_HALF_UP).doubleValue(), 100));
	}

	/**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }
}
