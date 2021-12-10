@extends('layouts.main')
@section('content')

<script>
function date_validate(new_event) {
    const today = new Date();
    let date = today.getFullYear() + '-' + (today.getMonth()+1) + '-' + today.getDate();
    let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    let date_time = date + 'T' + time;
    let start_date = new_event.start.value;
    let end_date = new_event.end.value;

    if(start_date=='' || end_date==''){
        alert("Üres dátumok!");
        return false;
    }
    else if(start_date>end_date){
        error="A befejezés nem lehet hamarabb, mint a kezdés!";
        document.getElementById("valid").innerHTML = error ;
        return false;
    }else if(start_date<=date_time){
        error="A start dátum nem lehet kissebb, mint az aktuális dátum!";
        document.getElementById("valid").innerHTML = error ;
        return false;
    }else if(end_date<=date_time){
        error="A befejezési dátum nem lehet hamarabb, mint az aktuális dátum!";
        document.getElementById("valid").innerHTML = error ;
        return false;
    }else{
        return true;
    }
}
</script>


<div class="container">
    <form action="{{ route('new_event') }}" method="POST" name="new_event">
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

        <div class="error-msg" id="valid">
        </div>

        <div>
            <input type="submit" value="Rögzítés" onClick="date_validate(new_event)" class="btn btn-page">
        </div>
    </div>
    </form>
</div>


@endsection
