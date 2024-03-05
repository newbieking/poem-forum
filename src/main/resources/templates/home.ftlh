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

    <link rel="stylesheet" href="index.css">

</head>
<body>


<div class="container">

    <#include "nav.ftlh">

    <#include "search.ftlh">


    <div class="container">
        <#list poems as poem>
            <div class="row justify-content-center">
                <div class="col-sm-6 mb-3 mb-sm-0">
                    <div class="card poem-card">
                        <a href="poem/${poem.id}?token=${token}" class="card-body">
                            <h5 class="card-title">《${poem.title}》</h5>
                            <p class="card-text">${poem.content}</p>
                            <span>${poem.author}</span>
                        </a>
                    </div>
                </div>
            </div>
        </#list>
    </div>


    <div class="position-fixed top-0 end-0 d-none d-lg-block">
        <div class="border border-3">
            <div class="row justify-content-center">
                <div class="">
                    <div class="card poem-card">
                        <div class="card-body">
                            <h5 class="card-title">Top 排行榜</h5>
                        </div>
                    </div>
                </div>
            </div>
            <#list topPoems as topPoem>
                <div class="row justify-content-center">
                    <div class="">
                        <div class="card poem-card">
                            <a href="poem/${topPoem.id}?token=${token}" class="card-body">
                                <h6 class="card-title">《${topPoem.title}》</h6>
                                <span>${topPoem.author}</span>
                            </a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>

</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>