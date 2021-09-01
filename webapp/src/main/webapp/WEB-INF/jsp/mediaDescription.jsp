<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title><c:out value="${media.title}"/> &#8226; PopCult</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-8 clearfix">
        <img src="${media.image}" class="img-fluid img-thumbnail float-left mr-2" width="20%" alt="Media Image">
        <div class="list-group list-group-horizontal">
            <h1><c:out value="${media.title}"/></h1>
            <h4 class="mx-3 mt-3">&#8226;</h4>
            <h4 class="mt-3">${media.releaseYear}</h4>
        </div>
        <p class="text-justify">${media.description}</p>
    </div>
</div>
</body>
</html>