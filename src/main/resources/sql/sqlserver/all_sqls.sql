### 定义order排序处理规则
#define order()
#if(orderColumns?? && orderTypes??)
 #for(col:orderColumns)#(for.first?" order by ":"")#(col) #if(orderTypes!=null&&orderTypes.length>0)#(orderTypes[for.index])#end #(for.last?"":",")#end
#end
#end

### 定义sql的where条件处理规则
#define where()
#if(myparas)
 where 1=1 
#for(myp:myparas)
 #((or??)?((for.index==0)?' and (':' or '):' and ') #(myp.key) #(customCompare?"":"=") #sqlValue(myp.value) #((for.index==for.size-1)?((or??)?")":""):"")
#end
 #@order() 
#else
 #@order()
#end
#end

#namespace("common")
#include("common.sql")
#end

#namespace("user.auth")
#include("user_auth.sql")
#end

#namespace("mall.goods")
#include("mall_goods.sql")
#end

#namespace("mall.goodstype")
#include("mall_goodstype.sql")
#end

#namespace("mall.brand")
#include("mall_brand.sql")
#end

#namespace("wechat")
#include("wechat.sql")
#end

#namespace("user")
#include("user.sql")
#end

