@extends('layouts.main')
@section('content')
<div class="container">
    <div class="row">
        <div class="col p-5 text-center">
            <div class="card-deck">
                <div class="card" style="width: 18rem;">
                    <div class="card-header h4 text-center"> 
                        Regisztráció
                    </div>
                    <div class="card-body">
                    <p class="card-text "> 
                        <h3 class="text-center h5"> Pár másodperc és adat megadása után már használhatod is az alkalmazást!
                    </p>
                    </div>
                </div>  
            </div>
        </div>
        <div class="col p-5 text-center">
            <form>
                <div class="mb-3">
                    <label class="form-label">Név*</label>
                    <input type="email" class="form-control" id="name" aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email cím*</label>
                    <input type="email" class="form-control" id="email" aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label class="form-label">Másodlagos email cím</label>
                    <input type="email" class="form-control" id="secound_email" aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label class="form-label">Jelszó*</label>
                    <input type="password" class="form-control" id="password">
                </div>
                <div class="mb-3">
                    <label class="form-label">Jelszó megerősítése*</label>
                    <input type="password" class="form-control" id="password_confim">
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="aszf">
                    <label class="form-check-label"> Elfogadod az oldalra vonatkozó <a href="#"> ASZF</a>-et? * </label>
                </div>
                <button type="submit" class="btn btn-primary">Regisztráció</button>
            </form>
        </div>
        <div class="col p-5 text-center">
            <div class="card-deck">
                <div class="card" style="width: 18rem;">
                    <div class="card-header h5 text-center"> 
                        Segítség a regisztrációhoz
                    </div>
                    <div class="card-body">
                    <p class="card-text "> 
                        <ul>
                            <li>A * -al jelölt mezők kitöltése kötelező</li>
                            <li>Kérlek valós e-mail címet adj meg!</li>
                        </ul>
                    </p>
                    </div>
                    <div class="card-body">
                    <p class="card-text "> 
                        <ul>
                            <li>Figyelj jelszavad erősségére!</li>
                            <li>A jelszavad tartalmazzon betűket, számokat és minimum legyen 8 karakter</li>
                        </ul>
                    </p>
                    </div>
                </div>  
            </div>
        </div>
    </div>
</div>
@endsection