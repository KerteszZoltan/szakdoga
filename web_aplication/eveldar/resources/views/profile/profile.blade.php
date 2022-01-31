@extends('layouts.main')
@section('content')
<div class="profile-container">
    <div class="card profile-card">
        <div class="card-titlet">
            <img class="card-img" src="{{asset('/images/app_icon.png')}}">
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col col-right">Bejelentkezett felhasználó</div>
                <div class="col col-left">{{ auth()->user()->name }}</div>
            </div>
            <div class="row">
                <div class="col col-right">Használatban lévő email cím</div>
                <div class="col col-left">{{ auth()->user()->email }}</div>

            </div>
            <div class="row">
                <div class="col col-right">Regisztráció dátuma</div>
                <div class="col col-left">{{ auth()->user()->created_at }}</div>
            </div>
            <div class="row profile-other">
                <div class="col-lg-2">
                    <div class="nav-item dropdown dropdown-profile">
                        <a class="nav-link dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            További lehetőségek
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="{{ route('modify_profile') }}">Profil módosítás</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item error-msg" href="{{ route('delete_profil') }}">Profil törlése</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
