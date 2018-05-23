package com.github2136.wardrobe.model.util;

/**
 * 错误响应
 * Created by yb on 2017/8/18.
 */

public class ErrorResponse {
    private Integer code;

    private String error;
    private String errorMsg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        if (code != null) {
            switch (code) {
                case 1:
                    return "服务器内部错误或者参数错误";
                case 100:
                    return "无法建立 TCP 连接到 LeanCloud 服务器";
                case 101:
                    return "查询的 Class 不存在";
                case 103:
                    return "非法的 Class 名称";
                case 104:
                    return "缺少 objectId";
                case 105:
                    return "无效的 key 名称";
                case 106:
                    return "无效的 Pointer 格式";
                case 107:
                    return "无效的 JSON 对象";
                case 108:
                    return "此 API 仅供内部使用";
                case 109:
                    return "共享的 Class 无权限执行此操作";
                case 111:
                    return "想要存储的值不匹配列的类型";
                case 112:
                    return "推送订阅的频道无效";
                case 113:
                    return "Class 中的某个字段设定成必须";
                case 114:
                    return "iOS 推送存储的 deviceToken 无效";
                case 116:
                    return "要存储的对象超过了大小限制";
                case 117:
                    return "更新的 Key 是只读属性";
                case 119:
                    return "该操作无法从客户端发起";
                case 120:
                    return "查询结果无法从缓存中找到";
                case 121:
                    return "JSON 对象中 key 的名称不能包含 $ 和 . 符号";
                case 122:
                    return "无效的文件名称";
                case 123:
                    return "ACL 格式错误";
                case 124:
                    return "请求超时";
                case 125:
                    return "电子邮箱地址无效";
                case 126:
                    return "无效的用户 Id";
                case 127:
                    return "手机号码无效";
                case 128:
                    return "无效的 Relation 数据";
                case 137:
                    return "违反 class 中的唯一性索引约束";
                case 139:
                    return "角色名称非法";
                case 140:
                    return "额度使用完";
                case 141:
                    return "云引擎调用超时";
                case 142:
                    return "云引擎校验错误";
                case 145:
                    return "本设备没有启用支付功能";
                case 150:
                    return "转换数据到图片失败";
                case 154:
                    return "超过应用阈值限制，例如短信消费超过每日最大上限等通常可以在应用设置的服务阈值菜单修改上限值";
                case 160:
                    return "账户余额不足";
                case 200:
                    return "没有提供用户名，或者用户名为空";
                case 201:
                    return "没有提供密码，或者密码为空";
                case 202:
                    return "用户名已经被占用";
                case 203:
                    return "电子邮箱地址已经被占用";
                case 204:
                    return "没有提供电子邮箱地址";
                case 205:
                    return "找不到电子邮箱地址对应的用户";
                case 206:
                    return "没有提供 session";
                case 207:
                    return "只能通过注册创建用户，不允许第三方登录";
                case 208:
                    return "第三方帐号已经绑定到一个用户，不可绑定到其他用户";
                case 210:
                    return "用户名和密码不匹配";
                case 211:
                    return "用户名不存在";
                case 212:
                    return "请提供手机号码";
                case 213:
                    return "手机号码对应的用户不存在";
                case 214:
                    return "手机号码已经被注册";
                case 215:
                    return "未验证的手机号码";
                case 216:
                    return "未验证的邮箱地址";
                case 217:
                    return "无效的用户名，不允许空白用户名";
                case 218:
                    return "无效的密码，不允许空白密码";
                case 219:
                    return "该用户账户被云端暂时锁定";
                case 250:
                    return "连接的第三方账户没有返回用户唯一标示 id";
                case 251:
                    return "无效的账户连接，一般是因为 access token 非法引起的";
                case 252:
                    return "无效的微信授权信息";
                case 300:
                    return "CQL 语法错误";
                case 301:
                    return "新增对象失败，通常是数据格式问题";
                case 302:
                    return "无效的 GeoPoint 类型，请确保经度在 -180 到 180 之间，纬度在 -90 到 90 之间";
                case 303:
                    return "插入数据库失败，一般是数据格式或者内部错误，通常错误里包含更具体的错误信息";
                case 304:
                    return "数据操作错误，一般是语法错误或者内部异常，请及时联系我们";
                case 305:
                    return "根据 where 条件更新或者删除对象不起作用，通常是因为条件不满足";
                case 401:
                    return "未经授权的访问，没有提供 App id，或者 App id 和 App key 校验失败，请检查配置";
                case 403:
                    return "控制台中的相关服务选项未打开";
                case 429:
                    return "超过应用的流控限制";
                case 430:
                    return "超过 REST API 上传文件流控限制";
                case 431:
                    return "超过云引擎 hook 调用流控限制";
                case 502:
                    return "服务器维护中";
                case 503:
                    return "应用被临时禁用或者进入只读状态";
                case 511:
                    return "该请求 API 暂时不可用";
                case 524:
                    return "Web 服务器与后端应用服务器通讯失败";
                case 529:
                    return "当前 IP 超过并发限制";
                case 600:
                    return "无效的短信签名";
                case 601:
                    return "发送短信过于频繁";
                case 602:
                    return "发送短信或者语音验证码失败";
                case 603:
                    return "无效的短信验证码";
                case 604:
                    return "找不到自定义的短信模板";
                case 605:
                    return "短信模板未审核";
                case 606:
                    return "渲染短信模板失败";
                case 608:
                    return "缺少图形验证码功能要求的 token";
                case 700:
                    return "无效的查询或者排序字段";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
