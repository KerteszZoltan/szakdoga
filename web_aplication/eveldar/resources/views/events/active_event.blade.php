@extends('layouts.main')
@section('content')

<div class="event-container">
    <div class="label-text pad10">
        Aktív események
    </div>

@if ($events->count())
    @foreach ( $events as $event )
    <div class="card event-card">
            <div class="card-header">
            {{ $event->topic }}
            </div>
            @if ($event->description)
            <div class="card-body">
                {{ $event->description }}
            </div>
            @endif
            <div class="card-footer">
                Kezdési dátum: {{ $event->start }}
            </div>
            <div class="card-footer">
                Befejezési dátum: {{ $event->end }}
            </div>
            <div class="card-footer">
                <form action="{{ route('modify_event', $event->id) }}" method="get">
                    @csrf
                    <button class="btn btn-page" type="submit">Módosítás</button>
                </form>
            </div>
    </div>
    @endforeach
@else
    <div class="card-body error-msg">
        Jelenleg még nem töltöttél fel a rendszerbe eseményt!
    </div>
</div>
@endif
</div>
<div class="event-foot">
    {{ $events->links() }}
</div>
@endsection
