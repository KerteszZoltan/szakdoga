@extends('layouts.main')
@section('content')
<div class="container">
    <div class="row">
        <div class="col text-center">
            <div class="card-deck">
                <div class="card" style="width: 18rem;">
                    <div class="card-title h4 text-center">
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
        <div class="col text-center">
            <form action="{{ route('register') }}" method="POST">
                @csrf
                <div class="card">
                <div class="mb-3">
                    <label class="form-label">Név*</label>
                    <input type="text" class="form-control @error('name') form-error @enderror" name="name" aria-describedby="emailHelp" value="{{ old('name') }}">
                    @error('name')
                        <div class="error-msg">
                            A név mező kitöltése kötelező
                        </div>
                    @enderror
                </div>
                <div class="mb-3">
                    <label class="form-label">Email cím*</label>
                    <input type="email" class="form-control @error('email') form-error @enderror" name="email" aria-describedby="emailHelp" value="{{ old('email') }}">
                    @error('email')
                        <div class="error-msg">
                            Az E-mailcím kitöltése kötelező
                        </div>
                    @enderror
                </div>
                <div class="mb-3">
                    <label for="password" class="sr-only">Jelszó</label>
                    <input type="password" name="password" id="password" class="form-control @error('password') form-error @enderror" value="">

                    @error('password')
                        <div class="error-msg">
                            A jelszó kitöltése kötelező!
                        </div>
                    @enderror
                </div>
                <div class="mb-3">
                    <label for="password_confirmation" class="sr-only">Jelszó megerősítés</label>
                    <input type="password" name="password_confirmation" id="password_confirmation" class="form-control @error('password_confirmation') form-error @enderror" value="">

                    @error('password_confirmation')
                        <div class="error-msg">
                            A két jelszó nem egyezik!
                        </div>
                    @enderror
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" name="aszf">
                    <label class="form-check-label @error('aszf') form-error @enderror"> Elfogadod az oldalra vonatkozó <a href="#"> ASZF</a>-et? * </label>
                    @error('aszf')
                        <div class="error-msg">
                            Kérlek olvasd el és fogadd el az Álltalános Szerződési Feltételeket!
                        </div>
                    @enderror
                </div>
                <input type="hidden" name="user_status_id" value="1"></label>
                <button type="submit" class="btn btn-page">Regisztráció</button>
                </div>
            </form>
        </div>
        <div class="col text-center">
            <div class="card-deck">
                <div class="card" style="width: 18rem;">
                    <div class="card-title h4 text-center">
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
