<%-- 
    Document   : index
    Created on : Dec 13, 2007, 1:48:49 AM
    Author     : Kop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Hello World!</h2>
        <form name="form" method="post" action="FrontController">
            <label>Original Currency
                <select name="OriginalCurrency" id="originalCurrency">
                    <option value="SEK">SEK</option>
                    <option value="USD">USD</option>
                    <option value="EUR">EUR</option>
                    <option value="JPY">JPY</option>
                    <option value="CNY">CNY</option>
                </select>
            </label>
            <label>Target Currency
                <select name="TargetCurrency" id="targetCurrency">
                    <option value="SEK">SEK</option>
                    <option value="USD">USD</option>
                    <option value="EUR">EUR</option>
                    <option value="JPY">JPY</option>
                    <option value="CNY">CNY</option>
                </select>
            </label>
            <p>
                <label>Value of Original Currency
                    <input type="text" name="Value" id="inputValue">
                </label>
            </p>
            <p>
                <label>
                    <input type="submit" name="submitButton" id="submitButton" value="Submit">
                </label>
            </p>            
        </form>
        <p>Target Value: ${message} </p>
    </body>
</html>
