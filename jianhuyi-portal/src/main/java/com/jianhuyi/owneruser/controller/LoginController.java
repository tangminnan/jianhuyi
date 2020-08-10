package com.jianhuyi.owneruser.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.jianhuyi.common.annotation.Log;
import com.jianhuyi.common.controller.BaseController;
import com.jianhuyi.common.utils.MD5Utils;
import com.jianhuyi.common.utils.ShiroUtils;
import com.jianhuyi.information.domain.FunctionSetDO;
import com.jianhuyi.information.service.FunctionSetService;
import com.jianhuyi.owneruser.comment.GenerateCode;
import com.jianhuyi.owneruser.dao.OwnerUserDao;
import com.jianhuyi.owneruser.domain.OwnerUserDO;
import com.jianhuyi.owneruser.service.OwnerUserService;
import com.jianhuyi.smsservice.service.ISMSService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OwnerUserDao ownerUserMapper;

    @Autowired
    OwnerUserService userService;
    @Autowired
    private ISMSService sMSService;
    @Autowired
    private FunctionSetService functionSetService;


    @Log("密码登录")
    @PostMapping("/loginP")
    Map<String, Object> loginP(String phone, String password) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        password = MD5Utils.encrypt(phone, password);
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            if (phone == null || "".equals(phone)) {
                map.put("code", -1);
                map.put("msg", "手机号码不能为空");
                map.put("data", "");
            } else {
                Map<String, Object> mapP = new HashMap<String, Object>();
                mapP.put("username", phone);
                boolean flag = userService.exit(mapP);
                if (!flag) {
                    map.put("code", -1);
                    map.put("msg", "该手机号码未注册");
                    map.put("data", "");
                } else {
                    OwnerUserDO udo = userService.getbyname(phone);
                    if (udo.getDeleteFlag() == 0) {
                        map.put("code", -1);
                        map.put("msg", "禁止登录，请联系客服");
                        map.put("data", "");
                    } else {
                        subject.login(token);
                        udo.setLoginTime(new Date());
                        userService.update(udo);

                        message.put("id", udo.getId());
                        message.put("nickname", udo.getNickname());
                        message.put("heardUrl", udo.getHeardUrl());
                        message.put("loginTime", udo.getLoginTime());

                        map.put("data", message);
                        map.put("code", 0);
                        map.put("msg", "登录成功");
                    }
                }

            }
        } catch (AuthenticationException e) {
            map.put("msg", "用户或密码错误");
            map.put("data", "");
            map.put("code", -1);
        }
        return map;
    }
   
 /*   @Log("发送验证码")
    @PostMapping("/captcha")
    Map<String, String> captcha(String phone, String type) {
        Map<String, String> message = new HashMap<>();
        try {
            if (phone == null || "".equals(phone)) {
                message.put("msg", "手机号码不能为空");
            } else {
                SMSTemplate contentType = SMSContent.ZHUCE;
                if ("0".equals(type)) {
                    contentType = SMSContent.ZHUCE;//注册
                }
                if ("1".equals(type)) {
                    contentType = SMSContent.LOGIN;//登录
                }
                
                Map<String, Object> map = sMSService.sendCodeNumber(SMSPlatform.jianhuyi, phone, contentType);
                if (map == null) {
                    message.put("msg", "验证码发送出现问题,请三分钟后再试");
                } else {
	                String code = map.get("randomCode").toString();
                	//String code = "666666";
	                Subject subject = SecurityUtils.getSubject();
	                subject.getSession().setAttribute("sys.login.check.code", phone + code);
	                message.put("msg", "发送成功");
	                message.put("sessionId",subject.getSession().getId().toString());
                }
            }
        } catch (Exception e) {
            logger.info("SMS==================验证码发送出现问题========" + phone + "======");
            message.put("msg", "验证码发送出现问题,请三分钟后再试");
        }
        return message;
    }*/
    
    /*
    pom.xml
    <dependency>
      <groupId>com.aliyun</groupId>
      <artifactId>aliyun-java-sdk-core</artifactId>
      <version>4.0.3</version>
    </dependency>
    */

    /**
     * @param phone 手机号
     * @param type  类型 0：注册   1：登录	2：密码重置
     * @说明 发送验证码
     */
    @Log("发送验证码")
    @PostMapping("/getSms")
    static Map<String, Object> getSms(String phone, String type) {
        Map<String, String> message = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            if (phone == null || "".equals(phone)) {
                map.put("code", -1);
                map.put("msg", "手机号码不能为空");
                map.put("data", "");
            } else {
                DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4G6bVPTi1kdS9DVzD4Us", "8UzvN8UI0iHxIbC5DTc15Zg4TlZRmU");
                IAcsClient client = new DefaultAcsClient(profile);

                Integer templateParam = (int) ((Math.random() * 9 + 1) * 100000);
                System.out.println("=================================" + templateParam + "===================================");

                CommonRequest request = new CommonRequest();
                //request.setProtocol(ProtocolType.HTTPS);
                request.setMethod(MethodType.POST);
                request.setDomain("dysmsapi.aliyuncs.com");
                request.setVersion("2017-05-25");
                request.setAction("SendSms");

                request.putQueryParameter("PhoneNumbers", phone);

                request.putQueryParameter("SignName", "新视能");

                if ("0".equals(type)) {
                    request.putQueryParameter("TemplateCode", "SMS_162732611");
                } else if ("1".equals(type)) {            //登陆
                    request.putQueryParameter("TemplateCode", "SMS_163720480");
                } else if ("2".equals(type)) {            //重置密码
                    request.putQueryParameter("TemplateCode", "SMS_163720481");
                }

                request.putQueryParameter("TemplateParam", "{\"code\":\"" + templateParam + "\"}");


                CommonResponse response = client.getCommonResponse(request);

                Subject subject = SecurityUtils.getSubject();
                subject.getSession().setAttribute("sys.login.check.code", phone + templateParam);

                message.put("sessionId", subject.getSession().getId().toString());
                map.put("code", 0);
                map.put("data", message);
                map.put("msg", "发送成功");
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return map;
    }


    @Log("微信登录")
    @PostMapping("/loginWechat")
    Map<String, Object> loginWechat(String openId, String heardUrl, String nickname) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        try {

            OwnerUserDO ownerUserDO = userService.getbyopenid(openId);
            if (ownerUserDO != null) {
                String phone = ownerUserDO.getPhone();
                String password = ownerUserDO.getPassword();

                UsernamePasswordToken token = new UsernamePasswordToken(openId, openId);

                subject.getSession().setAttribute("token", token);
                subject.login(token);

                message.put("sessionId", subject.getSession().getId().toString());
                message.put("ownerUserDO", ownerUserDO);
                map.put("code", 0);
                map.put("msg", "微信登录成功");
                map.put("data", message);
            } else {
                OwnerUserDO users = new OwnerUserDO();
                users.setDeleteFlag(1);
                users.setRegisterTime(new Date());
                Long userId = GenerateCode.gen16(8);
                users.setUserId(userId);
                users.setHeardUrl(heardUrl);
                users.setNickname(nickname);
                users.setOpenId(openId);
                users.setUsername(openId);
                users.setPassword(openId);
                userService.save(users);
                UsernamePasswordToken token = new UsernamePasswordToken(openId, openId);

                subject.login(token);
                System.out.println("==========users==========" + users);
                map.put("code", 0);
                map.put("msg", "微信登录成功");
                map.put("data", users);
            }

        } catch (AuthenticationException e) {
            map.put("code", -1);
            map.put("msg", "异常！请重新登录尝试");
            map.put("data", "");
        }
        return map;
    }

    @Log("qq登录")
    @PostMapping("/loginqq")
    Map<String, Object> loginqq(String unionid, String heardUrl, String nickname) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        try {

            OwnerUserDO ownerUserDO = userService.getbyunionid(unionid);
            if (ownerUserDO != null) {

                String phone = ownerUserDO.getPhone();
                String password = ownerUserDO.getPassword();

                UsernamePasswordToken token = new UsernamePasswordToken(unionid, unionid);

                subject.getSession().setAttribute("token", token);
                subject.login(token);

                message.put("sessionId", subject.getSession().getId().toString());
                message.put("ownerUserDO", ownerUserDO);
                map.put("code", 0);
                map.put("msg", "QQ登录成功");
                map.put("data", message);
            } else {
                OwnerUserDO users = new OwnerUserDO();
                users.setDeleteFlag(1);
                users.setRegisterTime(new Date());
                Long userId = GenerateCode.gen16(8);
                users.setUserId(userId);
                users.setHeardUrl(heardUrl);
                users.setNickname(nickname);
                users.setUnionid(unionid);
                users.setUsername(unionid);
                users.setPassword(unionid);
                userService.save(users);
                UsernamePasswordToken token = new UsernamePasswordToken(unionid, unionid);

                subject.login(token);
                System.out.println("==========users==========" + users);
                map.put("code", 0);
                map.put("msg", "QQ登录成功");
                map.put("data", users);
            }

        } catch (AuthenticationException e) {
            map.put("code", -1);
            map.put("msg", "异常！请重新登录尝试");
            map.put("data", "");
        }
        return map;
    }


    @Log("验证码登录")
    @PostMapping("/loginC")
    Map<String, Object> loginC(String phone, String codenum) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String msg = "";
        Subject subject = SecurityUtils.getSubject();

        Object object = subject.getSession().getAttribute("sys.login.check.code");
        try {
            if (object != null) {
                String captcha = object.toString();
                if (captcha == null || "".equals(captcha)) {
                    map.put("msg", "验证码已失效，请重新点击发送验证码");
                    map.put("code", -1);
                    map.put("data", "");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        map.put("msg", "手机验证码错误");
                        map.put("code", -1);
                        map.put("data", "");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", phone);
                        boolean flag = userService.exit(mapP);
                        if (!flag) {
                            map.put("msg", "该手机号码未注册");
                            map.put("code", -1);
                            map.put("data", "");
                        } else {
                            OwnerUserDO udo = userService.getbyname(phone);
                            if (udo == null || udo.getDeleteFlag() == 0) {
                                map.put("msg", "禁止登录，请联系客服");
                                map.put("code", -1);
                                map.put("data", "");
                            } else {

                                String password = udo.getPassword();
                                UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
                                subject.login(token);

                                udo.setLoginTime(new Date());

                                userService.update(udo);
                                message.put("id", udo.getId());
                                message.put("nickname", udo.getNickname());
                                message.put("heardUrl", udo.getHeardUrl());
                                message.put("loginTime", udo.getLoginTime());
                                map.put("msg", "登录成功");
                                map.put("code", 0);
                                map.put("data", message);

                            }
                        }
                    }
                }
            } else {
                map.put("msg", "手机验证码错误");
                map.put("code", -1);
                map.put("data", "");
            }
        } catch (AuthenticationException e) {
            map.put("msg", "手机号或验证码错误");
            map.put("code", -1);
            map.put("data", "");
        }
        return map;
    }


    @Log("用户注册")
    @PostMapping("/register")
    Map<String, Object> register(String phone, String codenum, String password, String secondPassword) {
        Map<String, Object> message = new HashMap<>();
        if (StringUtils.isBlank(phone)) {
            message.put("msg", "手机号码不能为空");
            message.put("code", -1);
            message.put("data", "");
        } else {
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
                String captcha = object.toString();
                //String captcha = "666666";
                if (captcha == null || "".equals(captcha)) {
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                    message.put("code", -1);
                    message.put("data", "");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        message.put("msg", "手机验证码错误");
                        message.put("code", -1);
                        message.put("data", "");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();

                        mapP.put("phone", phone);
                        boolean flag = userService.exit(mapP);    //查手机号是否存在
                        if (flag) {
                            if (userService.list(mapP).get(0).getPassword() == null) {
                                password = MD5Utils.encrypt(phone, password);
                                secondPassword = MD5Utils.encrypt(phone, secondPassword);
                                if (password.equals(secondPassword)) {
                                    Map<String, Object> params = new HashMap<String, Object>();
                                    params.put("phone", phone);
                                    OwnerUserDO user = userService.list(params).get(0);
                                    System.out.println("=======phone===============" + phone);
                                    System.out.println("=======user===============" + user);
                                    Long userId = GenerateCode.gen16(8);
                                    user.setUserId(userId);
                                    user.setUsername(phone);
                                    user.setPhone(phone);
                                    user.setPassword(password);
                                    user.setDeleteFlag(1);
                                    user.setRegisterTime(new Date());
                                    if (userService.update(user) > 0) {
                                        FunctionSetDO functionSet = new FunctionSetDO();
                                        functionSet.setUserId(user.getId());
                                        functionSet.setFunctionSet(1);
                                        functionSetService.save(functionSet);
                                        message.put("msg", "注册成功");
                                        message.put("code", 0);
                                        message.put("data", "");
                                    } else {
                                        message.put("msg", "注册失败");
                                        message.put("code", -1);
                                        message.put("data", "");
                                    }
                                } else {
                                    message.put("msg", "两次密码不一致");
                                    message.put("code", -1);
                                    message.put("data", "");
                                }
                            } else {
                                message.put("msg", "手机号码已存在");
                                message.put("code", -1);
                                message.put("data", "");
                            }

                        } else {
                            password = MD5Utils.encrypt(phone, password);
                            secondPassword = MD5Utils.encrypt(phone, secondPassword);
                            if (password.equals(secondPassword)) {
                                OwnerUserDO udo = new OwnerUserDO();
                                Long userId = GenerateCode.gen16(8);
                                udo.setUserId(userId);
                                udo.setUsername(phone);
                                udo.setPhone(phone);
                                udo.setPassword(password);
                                udo.setDeleteFlag(1);
                                udo.setRegisterTime(new Date());
                                if (userService.save(udo) > 0) {
                                    FunctionSetDO functionSet = new FunctionSetDO();
                                    functionSet.setUserId(udo.getId());
                                    functionSet.setFunctionSet(1);
                                    functionSetService.save(functionSet);
                                    message.put("msg", "注册成功");
                                    message.put("code", 0);
                                    message.put("data", "");
                                } else {
                                    message.put("msg", "注册失败");
                                    message.put("code", -1);
                                    message.put("data", "");
                                }
                            } else {
                                message.put("msg", "两次密码不一致");
                                message.put("code", -1);
                                message.put("data", "");
                            }
                        }
                    }
                }
            } else {
                message.put("msg", "手机验证码错误");
                message.put("code", -1);
                message.put("data", "");
            }
        }
        return message;

    }
	   
	   
    /*@Log("用户注册")
    @PostMapping("/register")
    Map<String, String> register(String phone, String codenum,String password,String unionid, String openId) {
    	 Map<String, String> message = new HashMap<>();
        String msg = "";
        if (StringUtils.isBlank(phone)) {
            message.put("msg", "手机号码不能为空");
        }else{
        	 Subject subject = SecurityUtils.getSubject();
             Object object = subject.getSession().getAttribute("sys.login.check.code");
             if (object != null) {
             	String captcha = object.toString();
             	//String captcha = "666666";
                 if (captcha == null || "".equals(captcha)) {
                     message.put("msg", "验证码已失效，请重新点击发送验证码");
                 } else {
                     // session中存放的验证码是手机号+验证码
                     if (!captcha.equalsIgnoreCase(phone + codenum)) {
                         message.put("msg", "手机验证码错误");
                     } else{
                    	 Map<String, Object> mapP = new HashMap<String, Object>();
                         
                         mapP.put("username", phone);
                         boolean flag = userService.exit(mapP);	//查手机号是否存在
                        
                         boolean flag1 = userService.getUnionid(mapP);		//查qq标识是否存在
                         System.out.println(flag1);
                         boolean flag2 = userService.getopenId(mapP);		//查微信标识是否存在
                         System.out.println(flag2);
                         
                         List<OwnerUserDO> ownerUserDO= userService.list(mapP);
                         if ((flag && !flag1 )|| (flag && !flag2)) {
                        	 	
                	 		if(!flag1 && !flag2){
	                   			 
	                   			ownerUserDO.get(0).setOpenId(openId);
	                   			ownerUserDO.get(0).setUnionid(unionid);
	                   			ownerUserDO.get(0).setFlag(0);//默认模式
	                       		ownerUserDO.get(0).setDeleteFlag(1);
	                       		ownerUserDO.get(0).setUsername(phone);
	                       		 
	                       		if (userService.updateUser(ownerUserDO.get(0))>0) {
	                                    message.put("msg", "注册成功");
	                                } else {
	                                    message.put("msg", "注册失败");
	                            }
	                   		 }else if(!flag1){
	            	 			ownerUserDO.get(0).setUnionid(unionid);	//qq注册标识
	            	 			ownerUserDO.get(0).setUsername(phone);
	            	 			ownerUserDO.get(0).setFlag(0);//默认模式
	                       		ownerUserDO.get(0).setDeleteFlag(1);
	                       		 
	                       		 if (userService.updateUser(ownerUserDO.get(0))>0) {
	                                    message.put("msg", "注册成功");
	                                } else {
	                                    message.put("msg", "注册失败");
	                            }
	            	 		}else if(!flag2){   
	                   			ownerUserDO.get(0).setOpenId(openId);
	                   			ownerUserDO.get(0).setUsername(phone);
	                   			ownerUserDO.get(0).setFlag(0);//默认模式
	                       		ownerUserDO.get(0).setDeleteFlag(1);
	                       		 
	                       		 if (userService.updateUser(ownerUserDO.get(0))>0) {
	                                    message.put("msg", "注册成功");
	                                } else {
	                                    message.put("msg", "注册失败");
	                            }
	                   		 }
                       		
                         }else if(flag && flag1 && flag2){
                        	  message.put("msg", "用户已存在");
                         }else if(!flag && !flag1 && !flag2){
                        		 OwnerUserDO udo = new OwnerUserDO();
                            	 
                        		 Long userId = GenerateCode.gen16(8);
                                 udo.setUnionid(unionid);	//qq注册标识
                                 udo.setOpenId(openId);		//微信注册标识
                                 
                                 udo.setUserId(userId);
                                 udo.setUsername(phone);
                                 udo.setPhone(phone);
                                 udo.setPassword(password);
                                 udo.setFlag(0);//默认模式
                                 udo.setDeleteFlag(1);
                                 udo.setRegisterTime(new Date());
                                 if (userService.save(udo) > 0) {
                                     message.put("msg", "注册成功");
                                 } else {
                                     message.put("msg", "注册失败");
                                 }
                        	 	}	
                        	 }
                        
                         }
                     }
                 }
          return message;             
    }*/

    @Log("忘记密码")
    @PostMapping("/retpwd")
    Map<String, Object> retpwd(String phone, String password, String codenum) {
        Map<String, Object> message = new HashMap<>();
        if (StringUtils.isBlank(phone)) {
            message.put("msg", "手机号码不能为空");
            message.put("code", -1);
            message.put("data", "");
        } else {
            OwnerUserDO udo = userService.getbyname(phone);
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
                String captcha = object.toString();
                if (captcha == null || "".equals(captcha)) {
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                    message.put("code", -1);
                    message.put("data", "");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        message.put("msg", "手机验证码错误");
                        message.put("code", -1);
                        message.put("data", "");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", phone);
                        boolean flag = userService.exit(mapP);
                        if (!flag) {
                            message.put("msg", "该手机号码未注册");
                            message.put("code", -1);
                            message.put("data", "");
                        } else {
                            password = MD5Utils.encrypt(phone, password);
                            udo.setPassword(password);
                            if (userService.update(udo) > 0) {
                                message.put("msg", "修改成功");
                                message.put("code", 0);
                                message.put("data", "");
                            }
                        }
                    }
                }
            } else {
                message.put("msg", "手机验证码错误");
                message.put("code", -1);
                message.put("data", "");
            }
        }
        return message;
    }

    @Log("登出")
    @GetMapping("/logout")
    Map<String, Object> logout() {
        Map<String, Object> message = new HashMap<>();
        ShiroUtils.logout();
        message.put("msg", "登出成功");
        message.put("code", 0);
        message.put("data", "");
        return message;
    }

}
