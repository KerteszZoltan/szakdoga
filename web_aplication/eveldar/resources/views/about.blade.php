@extends('layouts.main')
@section('content')
<div class="about-container">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-tittle h4 text-center">
                    Meetinged lesz?<br>
                    Esetleg névnapi buliba vagy hivatalos?
                </div>
                <div class="card-body">
                <p class="card-text ">
                    <h3 class="text-center"> Mi segítünk észben tartani!
                </p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-tittle h4 text-center">
                    Valamit fel kell jegyezned valamit munka közben, amire otthon is szükséged lesz?<br>
                </div>
                <div class="card-body">
                <p class="card-text ">
                    <h3 class="text-center"> Velünk ezt is megoldhatod!
                </p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-tittle h4 text-center">
                    Elveszítetted a telefonod?<br>
                </div>
                <div class="card-body">
                <p class="card-text ">
                    <h3 class="text-center"> A feljegyzéseid így is elérheted a webes felületen!
                </p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-tittle h4 text-center">
                    Találkozol a boltban egy régi baráttal, aki szeretne meglátogatni?<br>
                </div>
                <div class="card-body">
                <p class="card-text ">
                    <h3 class="text-center"> Írd fel a telefonodba és nézd vissza akár számítógépről is, hogy mikor érkezik!
                </p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="eveldar-col">
            <div class="card">
                <img class="card-img" src="{{asset('/images/app_icon.png')}}">
                <div class="card-body">
                    <p class="card-text text-center h3">
                        Minden eseményed egy helyen!<br>
                        Velünk nem maradsz le semmiről,<br>
                        Nélkülünk mindenről!
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

@endsection
