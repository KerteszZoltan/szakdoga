<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eveldar</title>
    
    <link href="{{asset('css/app.css')}}" rel="stylesheet">
    <link rel="icon" href="{{ url('css/img/favicon.png') }}">

</head>
<body>
    <script src="{{asset('js/app.js')}}"></script>
<nav>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link" aria-current="page" href="{{ route('about') }}">About</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Események</a>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Aktív események</a></li>
                <li><a class="dropdown-item" href="#">Esemény hozzáadása</a></li>
                <li><a class="dropdown-item" href="#"></a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">Archív események</a></li>
            </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Profil</a>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Profil módosítása</a></li>
                <li><a class="dropdown-item" href="#"></a></li>
                <li><a class="dropdown-item" href="#"></a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">Profil törlése</a></li>
            </ul>
        </li>
        <li class="nav-item float-right">
        <form action="{{ route('register') }}" method="GET" class="form-inline">
            <button class="btn btn-primary" type="submit">Regisztráció</button>
        </form>
        </li>
    </ul>
</nav>
@yield('content')
</body>
</html>