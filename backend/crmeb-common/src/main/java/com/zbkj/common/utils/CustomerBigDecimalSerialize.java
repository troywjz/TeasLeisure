package com.zbkj.common.utils;
 
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
 
import java.math.BigDecimal;
import java.util.Objects;

/**
 *  BigDecimal 传输类型：String转数字
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
public class CustomerBigDecimalSerialize extends ValueSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializationContext context) throws JacksonException {
        if(Objects.nonNull(value)) {
            gen.writeNumber(value.stripTrailingZeros().doubleValue());
            //去除0后缀,如果想统一进行保留精度，也可以采用类似处理
        }else {//如果为null的话，就写null
            gen.writeNull();
        }
    }
}
