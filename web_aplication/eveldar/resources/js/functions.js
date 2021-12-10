function date_validate(new_event) {
    const today = new Date();
    let date = today.getFullYear() + '-' + (today.getMonth()+1) + '-' + today.getDate();
    let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    let date_time = date + 'T' + time;
    let start_date = new_event.start.value;
    let end_date = new_event.end.value;

    if(start_date==''||end_date==''){
        alert("Üres dátumok!");
        return false;
    }
    else if(start_date>end_date){
        error="A befejezés nem lehet hamarabb, mint a kezdés!";
        document.getElementById("valid").innerHTML = error ;
        return false;
    }else if(start_date<=date_time){
        alert("A start dátum nem lehet kissebb, mint az aktuális dátum!");
        return false;
    }else if(end_date<=date_time){
        alert("A befejezési dátum nem lehet hamarabb, mint az aktuális dátum!");
        return false;
    }else{
        return true;
    }
}
