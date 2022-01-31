@extends('layouts.main')
@section('content')
<div class="profile-container">
    <div class="card profile-card">
        <div class="card-titlet">
            <img class="card-img" src="{{asset('/images/app_icon.png')}}">
        </div>
        <div class="card-body">
            <form action="{{ route('modify_profile')}}" method="post">
                @csrf
            <div class="row">
                <div class="col col-right"> Név: </div>
                <div class="col col-left">
                    <input type="text" name="name" value="{{ auth()->user()->name }}">
                </div>
            </div>
            <div class="row">
                <div class="col col-right"> E-mail cím: </div>
                <div class="col col-left">
                    <input type="text" name="email" value="{{ auth()->user()->email }}">
                </div>

            </div>
            <div class="row">
                @if (auth()->user()->updated_at==auth()->user()->created_at)
                        <div class="col-lg-2">
                            Még nem volt módosítva a profil!
                        </div>
                    </div>
                @else
                    <div class="col col-right"> Módosítás dátuma</div>
                        <div class="col col-left">
                            {{ auth()->user()->updated_at }}
                        </div>
                    </div>
                @endif

            <div class="row profile-other">
                <div class="col-lg-2">
                    <input type="hidden" name="id" value="{{ auth()->user()->id }}">
                    <input class="btn btn-page" type="submit" value="Mentés">
                </div>
            </div>
        </form>
        </div>
    </div>
</div>
@endsection
