<?php

namespace App\Http\Controllers\Auth;

use App\Models\User;
use App\Mail\NewRegisteredUser;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Mail;

class RegisterController extends Controller
{
    public function __construct()
    {
        $this->middleware(['guest']);
    }

    public function index(){
        return view('auth.register');
    }

    public function store(Request $request){
        $this->validate($request,[
            'name'=>'required|max:255',
            'email'=>'required|email|max:255',
            'password'=>'required|confirmed',
            'aszf'=>'required',
        ]);
        $email=$request->email;
        $emailcheck=User::where('email','=',$email)->count();
        $bad_email='';
        if ($emailcheck>0) {
            return view('auth.register',[
                'bad_email' => $emailcheck,
                'respose' => $request,
            ]);
        }else{
            User::create([
                'name' => $request->name,
                'username' => $request->username,
                'email' => $request->email,
                'password' => Hash::make($request->password),
            ]);

            auth()->attempt($request->only('email', 'password'));
            $user=auth()->user();
            Mail::to($user)->send(new NewRegisteredUser(auth()->user()));

            return redirect()->route('profile');
        }

    }
}
