@extends('layouts.main')
@section('content')
<div class="profile-container">
    <div class="card profile-card danger-card">
        <div class="card-titlet">
            <img class="card-img" src="{{asset('/images/app_icon.png')}}">
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-lg-2">A törléshez kérlek igazold magad!</div>
            </div>
            <form action="{{ route('delete_profil') }}" method="post">
                @csrf
            <div class="row">
                <div class="col col-right">Jelenlegi jelszó:</div>
                <div class="col col-left">
                    <input type="password" name="password">
                </div>
            </div>
            <div class="row">
                <div class="col col-right">Jelszó még egyszer</div>
                <div class="col col-left">
                    <input type="password" name="password_confirmation">
                </div>
            </div>
            <div class="row profile-other">
                <div class="col-lg-2">
                    @error('password')
                        <div class="error-msg">
                            A két jelszó nem egyezik!
                        </div>
                    @enderror
                    @if(isset($error_password))
                        <div class="error-msg">
                            {{ $error_password }}
                        </div>
                    @endif
                    <input type="hidden" name="id" value="{{ auth()->user()->id }}">
                    <input class="btn btn-danger" type="submit" value="Törlés">
                </div>
            </div>
        </form>
        </div>
    </div>
</div>
@endsection
