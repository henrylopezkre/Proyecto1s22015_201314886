<%-- 
    Document   : main
    Created on : 29/08/2015, 05:55:50 PM
    Author     : Sorge
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/kube.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div>
            <form method="post" action="" class="forms">
                <label>
                    Email
                    <input type="email" name="user-email" class="width-50" />
                </label>
                <label>
                    Password
                    <input type="password" name="user-password" class="width-50" />
                </label>
                <p>
                    <button class="btn btn-blue">Log in</button>
                    <button class="btn">Cancel</button>
                </p>
            </form>
        </div>
    </body>
</html>
