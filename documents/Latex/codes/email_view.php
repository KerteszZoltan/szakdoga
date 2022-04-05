@component('mail::message')
# Uj esemeny

A kovetkezo esemenyt taroltuk el:<br>
Az esemeny neve: {{$event->topic}}<br>
@if ($event->description!='')
Az esemeny leirasa: {{$event->description}}<br>
@endif
Az esemeny kezdeti datuma: {{$event->start}}<br>
Az esemeny befejezesi datuma: {{$event->end}}<br>

@component('mail::button', ['url' => route('modify_event', $event->id)])
Tekintsd meg az esemenyt!
@endcomponent

Koszonettel,<br>
{{ config('app.name') }}
@endcomponent
