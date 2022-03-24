@component('mail::message')
# Üdvözöljük {{$user->name}} az Eveldar Calendar rendszerben!

Köszönjük, hogy minket választott az eseményeinek kezelésére!<br>


@component('mail::button', ['url' => 'http://192.168.0.216/eveldar/public/'])
Kezdjük el az események felírását!
@endcomponent

Köszönettel,<br>
{{ config('app.name') }}
@endcomponent
