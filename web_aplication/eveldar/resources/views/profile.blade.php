@extends('layouts.main')
@section('content')
<div class="profile-container">
    <div class="profile-card">
        <div class="card-titlet">
            <label value="">Bejelentkezett felhasználó: {{ auth()->user()->name }} </label>
        </div>
        <div class="card-body">
            <label for="">Használatban lévő e-mail cím: {{ auth()->user()->email }}</label>
        </div>
    </div>
</div>
@endsection
