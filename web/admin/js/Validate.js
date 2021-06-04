// 账号校验
function validateUserName(){
    var username = $("#username").val();
    if (username == "" || username == null){
        alert("账号名不能为空！")
        return false;
    } else if (username.length < 6) {
        alert("账号名不能少于6位")
        return false;
    }
    return true;
}

function validateUserPass(){
    var password = $("#password").val();
    if (password == "" || password == null){
        alert("密码不能为空！")
        return false;
    } else if (pass.length < 6) {
        alert("个人密码不能少于6位！")
        return false;
    }
    return true;
}

$(function (){
   $("#username").blur(function (){
       validateUserName();
    });
   $("#password").blur(function (){
       validateUserPass();
   });

   $("#myForm").submit(function (){
       return validateUserName() && validateUserPass();
   });
});