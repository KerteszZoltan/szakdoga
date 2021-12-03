@extends('layouts.main')
@section('content')


<div class="container">
    <form action="{{ route('new_event') }}" method="POST">
    <div class="card event-card">
        @csrf
        <div class="input-group mb-3">
            <span class="card-header event-card-header label-text" id="basic-addon1">Új esemény hozzáadása</span>
        </div>

        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon2">Esemény neve: </span>
            <input type="text" class="form-control" placeholder="pl. Találkozó" name="topic" value="{{ old('topic') }}">
        </div>
        @error('topic')
            <div class="error-msg">
                Az esemény neve nem lehet üres!
            </div>
        @enderror

        <div class="input-group event-text">
            <span class="input-group-text">Esemény leírása</span>
            <textarea class="form-control" name="description" value="{{ old('description') }}"></textarea>
        </div>

        <div class="input-group mb-3">
            <span class="input-group-text">Esemény kezdete:</span>
            <input type="datetime-local" class="form-control" name="start">
        </div>
        @error('start')
            <div class="error-msg">
                Az eseményhez tartozni kell kezdő dátumnak!
            </div>
        @enderror

        <div class="input-group mb-3">
            <span class="input-group-text">Esemény vége:</span>
            <input type="datetime-local" class="form-control" name="end">
        </div>
        @error('end')
            <div class="error-msg">
                Az eseményhez tartozni kell befejezési dátumnak!
            </div>
        @enderror

        <div>
            <input type="submit" value="Rögzítés" class="btn btn-page">
        </div>
    </div>
    </form>
</div>


@endsection
