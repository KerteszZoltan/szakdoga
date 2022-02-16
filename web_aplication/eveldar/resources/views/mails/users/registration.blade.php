@component('mail::message')
# Üdvözöljük {{$user->name}} az Eveldar Calendar rendszerben!

Köszönjük, hogy minket választott az eseményeinek kezelésére!<br>


@component('mail::button', ['url' => route('login')])
Kezdjük el az események felírását!
@endcomponent

Köszönettel,<br>
{{ config('app.name') }}
@endcomponent
