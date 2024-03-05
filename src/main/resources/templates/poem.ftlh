<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>

    <title>首页</title>
    <link rel="stylesheet" href="/index.css">

</head>
<body>


<div class="container">

    <#include "nav.ftlh">

    <#include "search.ftlh">

    <#if error??>
        <div class="container">
            <h2 class="text-center" style="color: red; font-weight: bold;">${error}</h2>
        </div>
    <#else>
    <#--poem-->
        <div class="container">
            <div class="row justify-content-center">
                <div class=" mb-3 mb-sm-0">
                    <div class="card poem-card">
                        <div class="card-body">
                            <h5 class="card-title">《${poem.title}》</h5>
                            <p class="card-text">${poem.content}</p>
                            <span>${poem.author}</span>
                            <#if !collected>
                                <form action="${ctx.contextPath}/collect" method="post">
                                    <input hidden name="token" type="text" value="${token}">
                                    <input hidden name="poemId" type="text" value="${poemId}">
                                    <button type="submit" class="btn btn-primary">收藏</button>
                                </form>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#--poem commemnt list-->
        <div class="container">
            <#list comments as comment>
                <div class="row justify-content-center">
                    <div class="col-sm-6 mb-3 mb-sm-0">
                        <div class="card poem-card">
                            <div class="card-body">
                                <h5 class="card-title">用户<b style="margin: 6px"><i>${comment.userId}</i></b>说:</h5>
                                <p class="card-text">${comment.content}</p>
                                <i>${comment.createTime?string("yyyy-MM-dd")!}</i>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    <#--comment input-->
        <div class="container-fluid">
            <form method="post" action="${ctx.contextPath}/comment" role="search">
                <textarea name="comment" class="form-control me-2 poem-comment"
                          placeholder="发布评论……"
                          aria-label="Comment"></textarea>
                <input hidden name="token" type="text" value="${token}">
                <input hidden name="poemId" type="text" value="${poemId}">
                <button class="col-2 form-control btn btn-outline-success" type="submit">评论</button>
            </form>
        </div>


    </#if>

</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>