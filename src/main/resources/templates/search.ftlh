<div class="container-fluid">
    <form method="get" action="/search" class="position-relative align-items-center" role="search">
        <input name="search" class="form-control me-2 poem-search" type="search" placeholder="Search"
               value="${search!''}"
               aria-label="Search">
        <input hidden name="token" type="text" value="${token}">
        <input class="form-control float-end position-absolute"
               type="submit"
               style="top:50%;transform: translateY(-50%);right:10px;width:23px; height:23px; background-image:url(https://ziyuan.guwendao.net/siteimg/docSearch230511.png); background-repeat:no-repeat; background-size:23px 23px; border:0;cursor:pointer;"
               value="">
    </form>
</div>