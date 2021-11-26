<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Eveldar</title>
    
    <link href="{{asset('css/app.css')}}" rel="stylesheet">
    <link rel="icon" href="{{url('images/app_icon.png')}}">


    <script src="js/app.js" async></script>

</head>
<body>
<nav class="navbar">
    <ul class="nav nav-tabs">
    <div class="nav-left-side">
        <li class="nav-item">
            <a class="nav-link" aria-current="page" href="{{ route('about') }}">About</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Események</a>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Aktív események</a></li>
                <li><a class="dropdown-item" href="#">Esemény hozzáadása</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">Lejárt események</a></li>
                <li><a class="dropdown-item" href="#">Teljesített események</a></li>
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
    </div>
    <div class="nav-right-side">
        <li class="nav-item">
        <form action="{{ route('register') }}" method="GET" class="form-inline">
            <button class="btn" type="submit">Regisztráció</button>
        </form>
        </li>
        <li class="nav-item">
        <form action="" method="GET" class="form-inline">
            <button class="btn" type="submit">Bejelentkezés</button>
        </form>
        </li>
    </div>
    </ul>
</nav>
@yield('content')
</body>
</html>