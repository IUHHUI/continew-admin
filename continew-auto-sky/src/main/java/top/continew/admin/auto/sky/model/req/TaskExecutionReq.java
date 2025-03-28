package top.continew.admin.auto.sky.model.req;


import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

/**
 * 创建或修改执行情况参数
 *
 * @author wjh
 * @since 2025/03/29 20:49
 */
@Data
@Schema(description = "创建或修改执行情况参数")
public class TaskExecutionReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}