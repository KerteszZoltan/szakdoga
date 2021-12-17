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
            <input class="form-control label-text" type="datetime" name="start" value="{{ $event->start }}" step="1">
        </div>
        <div class="card-footer">
            <label class="label-text">Esemény vége:</label>
            <input class="form-control label-text" type="datetime" name="end" value="{{ $event->end }}" step="1">
        </div>
        <div class="card-footer card-save">
            <input class="btn btn-page" type="submit" value="Mentés">
        </div>
        </form>
    </div>
</div>

@endsection
