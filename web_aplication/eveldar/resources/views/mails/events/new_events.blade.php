@component('mail::message')
# Új esemény

A következő eseményt tároltuk el:<br>
Az esemény neve: {{$event->topic}}<br>
@if ($event->description!='')
Az esemény leírása: {{$event->description}}<br>
@endif
Az esemény keezdeti dátuma: {{$event->start}}<br>
Az esemény befejezési dátuma: {{$event->end}}<br>

@component('mail::button', ['url' => "http://192.168.0.108/eveldar/public/event-{{$event->id}})"])
Tekintsd meg az eseményt!
@endcomponent

Köszönettel,<br>
{{ config('app.name') }}
@endcomponent
