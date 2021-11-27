@extends('layouts.main')
@section('content')
<div class="container login-container">
    <div class="col p-5 text-center">
        <form action="{{ route('login') }}" method="POST">
            <div class="card">
                <div class="card-title h2 p-3"> Bejelentkezés</div>
            @csrf
            <div class="login-card-body">
                @if (session('invalid'))
                <div class="error-msg">
                    {{session('invalid')}}
                </div>
                @endif
                <label class="form-label">Email cím:</label>
                <input class="form-control" type="email" name="email" id="email">
                <label class="form-label">Jelszó:</label>
                <input class="form-control" type="password" name="password" id="password">
                <div class="remember">
                <input type="checkbox" name="remember" id="remember">
                <label for="remember">Bejelentkezés megjegyzése</label>
                </div>
            </div>
            <div class="card-footer">
            <button type="submit" class="btn btn-page">Bejelentkezés</button>
            </div>
            </div>
        </form>
    </div>
</div>
@endsection
