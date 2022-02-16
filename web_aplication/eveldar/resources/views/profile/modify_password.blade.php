@extends('layouts.main')
@section('content')
<div class="profile-container">
    <div class="card profile-card">
        <div class="card-titlet">
            <img class="card-img" src="{{asset('/images/app_icon.png')}}">
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-lg-2">A jelszó modosításhoz igazold magad!</div>
            </div>
            <form action="{{route('password_update')}}" method="post">
                @csrf
            <div class="row">
                <div class="col col-right">Jelenlegi jelszó:</div>
                <div class="col col-left">
                    <input type="password" name="password">
                </div>
            </div>
            <div class="row">
                <div class="col col-right">Új jelszó</div>
                <div class="col col-left">
                    <input type="password" name="new_password">
                </div>
            </div>
            <div class="row">
                <div class="col col-right">Új jelszó megerősítése</div>
                <div class="col col-left">
                    <input type="password" name="new_password_confirmation">
                </div>
            </div>
            <div class="row profile-other">
                <div class="col-lg-2">
                    @error('new_password')
                        <div class="error-msg">
                            A két új jelszó nem egyezik!
                        </div>
                    @enderror
                    @if(isset($error_password))
                        <div class="error-msg">
                            {{ $error_password }}
                        </div>
                    @endif
                    <input type="hidden" name="id" value="{{ auth()->user()->id }}">
                    <input class="btn btn-page" type="submit" value="Mentés">
                </div>
            </div>
        </form>
        </div>
    </div>
</div>
@endsection
