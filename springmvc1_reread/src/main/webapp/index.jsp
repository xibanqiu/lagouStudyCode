
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>SpringMVC 测试页</title>


    <script type="text/javascript" src="/js/jquery.min.js" ></script>

    <script>

        $(function () {
            $("#ajaxBtn").bind("click",function () {
                $.ajax({
                    url:'/demo/handle14',
                    type:'POST',
                    data:'{"id":"1","name":"李四"}',
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (data) {
                        alert(data.name);
                    }
                })
            })
        })

    </script>
    <style>
        div{
            padding:10px 10px 0 10px;
        }
    </style>

</head>

<body>
<div>
    <h2>Spring MVC 请求参数绑定</h2>
    <fieldset>
        <p>测试用例：SpringMVC 对原生servlet api的支持</p>
        <a href="/demo/handle0?id=1">点击测试</a>
    </fieldset>
    <fieldset>
        <p>测试用例：SpringMVC 接收简单数据类型参数</p>
        <a href="/demo/handle06?id=1">点击测试</a>
    </fieldset>
    <fieldset>
        <p>测试用例：SpringMVC 使用@RequestParam 接收简单数据类型参数(形参名和参数名不一致)</p>
    </fieldset>

    <fieldset>
        <p>测试用例：SpringMVC接收pojo类型参数</p>
        <a href="/demo/handle07?id=1&name=zhangsan">点击测试</a>
    </fieldset>

    <fieldset>
        <p>测试用例：SpringMVC接收pojo包装类型参数</p>
        <a href="/demo/handle08?user.id=1&user.name=zhangsan">点击测试</a>
    </fieldset>

    <fieldset>
        <p>测试用例：SpringMVC接收日期类型参数</p>
        <a href="/demo/handle09?birthday=2019-10-08">点击测试</a>
    </fieldset>
</div>



<div>
    <h2>SpringMVC对Restful⻛格url的⽀持</h2>
    <fieldset>
        <p>测试⽤例：SpringMVC对Restful⻛格url的⽀持</p>

        <a href="/demo/handle10/10">rest_get测试</a> <br>

        <form method="post" action="/demo/handle11">
            <input type="text" name="name"/>
            <input type="submit" value="提交rest_post请求"/>
        </form>

        <form method="post" action="/demo/handle12/15/lisi">
            <input type="hidden" name="_method" value="put"/>
            <input type="submit" value="提交rest_put请求"/>
        </form>
        <form method="post" action="/demo/handle13/13">
            <input type="hidden" name="_method" value="delete"/>
            <input type="submit" value="提交rest_delete请求"/>
        </form>
    </fieldset>
</div>



<div>
    <h2>Ajax json交互</h2>
    <fieldset>
        <input type="button" id="ajaxBtn" value="ajax提交"/>
    </fieldset>
</div>


</body>
</html>
