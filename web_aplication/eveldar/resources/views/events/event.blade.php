@extends('layouts.main')
@section('content')

<div class="event-container">

    <div class="card event-type">
        @if ($type=='active')
            Aktív események listája
        @elseif ($type=='expired')
            Lejárt események listája
        @elseif ($type=='complete')
            Teljesített események listája
        @endif
    </div>

@if ($events->count())
    @foreach ( $events as $event )

    <div class="card event-card">
        <div class="row">
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
                <div class="row">
                @if ($type!='complete')
                <div class="col">
                <form action="{{ route('modify_event', $event->id) }}" method="get">
                    @csrf
                    <button class="btn btn-page" type="submit">Módosítás</button>
                </form>
                </div>
                @endif
                @if ($type!='complete')
                <div class="col">
                    <form action="{{ route('complete', $event->id) }}" method="post">
                        @csrf
                        <button class="btn btn-page" type="submit"> Teljesítés</button>
                    </form>
                </div>
                @endif
                <div class="col">
                    <form action="{{ route('delete', $event->id) }}" method="post">
                        @csrf
                        <button class="btn btn-page" type="submit"> Törlés</button>
                    </form>
                </div>
                </div>
            </div>
        </div>
    </div>
@endforeach
@else
    <div class="card event-type">
        <div class="error-msg">
            @if ($type=='active')
                Nincs aktív esemény a rendszerben!
            @elseif ($type=='expired')
                Nincs lejárt esemény a rendszerben!
            @elseif ($type=='complete')
                Nincs még telejesített eseményed!
            @endif
        </div>
    </div>

</div>
@endif
</div>
<div class="event-foot">
    {{ $events->links() }}
</div>
</div>
@endsection
