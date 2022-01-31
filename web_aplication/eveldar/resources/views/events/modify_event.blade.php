@extends('layouts.main')
@section('content')
<div class="event-container">
    <div class="card event-card">
        <form action=" {{ route('modify_event', $event->id) }} " method="POST">
            @csrf
        <div class="card-header">
            <label class="label-text">Esemény neve:</label>
            <input class="form-control label-text" type="text" name="topic" value="{{ $event->topic }}">
        </div>
        <div class="card-body">
            <label class="label-text">Esemény leírása:</label>
            <textarea class="form-control" name="description"> {{ $event->description }} </textarea>
        </div>
        <div class="card-footer">
            <label class="label-text">Esemény kezdete:</label>
            <input class="form-control label-text" type="datetime-local" name="start" value="{{old('start')?? date('Y-m-d\TH:i', strtotime($event->start)) }}" step="1">
            @error('start')
                <div class="error-msg">
                    Az eseményhez tartoznia kell kezdési dátumnak ami nem lehet hamarabb, mint az aktuális dátum!
                </div>
            @enderror
        </div>
        <div class="card-footer">
            <label class="label-text">Esemény vége:</label>
            <input class="form-control label-text" type="datetime-local" name="end" value="{{old('end')?? date('Y-m-d\TH:i', strtotime($event->end)) }}" step="1">
            @error('end')
                <div class="error-msg">
                    Az esemény végéhez tartoznia kell dátumnak, ami nem lehet hamarabbi, mint a kezdési dátum!
                </div>
            @enderror
        </div>
        <div class="card-footer card-save">
            <input class="btn btn-page" type="submit" value="Mentés">
        </div>
        </form>
    </div>
</div>

@endsection
