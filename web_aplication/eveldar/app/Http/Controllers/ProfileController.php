<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Laravel\Ui\Presets\React;

class ProfileController extends Controller
{
    public function __construct()
    {
        $this->middleware(['auth']);
    }
    public function index(){
        return view('profile.profile');
    }

    public function modify_index(){
        return view('profile.modify_profil');
    }

    public function update(Request $request){
        $this-> validate($request,[
            'name' => "required ",
            'email' => "required | email",
        ]);
        $id=$request->input('id');
        $profile = User::find($id);
        $profile->name = $request->input('name');
        $profile->email = $request->input('email');
        $profile->save();
        return redirect()->route('profile');
    }

    public function update_index(){
       return view('profile.modify_password');
    }

    public function password_update(Request $request){
        $this-> validate($request,[
            'password' => "required ",
            'new_password' => "required | confirmed",
        ]);
        $password=$request->input('password');
        $new_password=$request->input('new_password');
        $user_password=auth()->user()->password;
        if($password==$new_password){
            $error="A jelenlegi jelszó és az új jelszó nem lehet ugyan az!";
            return view('profile.modify_password')->with('error_password',$error);
        }else{
            if (Hash::check($password, $user_password)){
                $id=auth()->user()->id;
                $user=User::find($id);
                $user->password = Hash::make($request->input('new_password'));
                $user->save();
                auth()->logout();
                $msg="Kérlek jelentkezz be az új jelszóval!";
                return view('auth.login')->with('msg',$msg);
            }else{
                $error="A jelenlegi jelszó nem megfelelő!";
                return view('profile.modify_password')->with('error_password',$error);
            }
        }

    }

    public function confirm_delete(){
        return view('profile.confirm_delete');
    }

    public function delete(Request $request) {
        $this-> validate($request,[
            'password'=>'required|confirmed',
        ]);

        $id=$request->input('id');
        $password=$request->input('password');
        $user_password=auth()->user()->password;
        if (Hash::check($password, $user_password)){
            $user=User::find($id);
            $user=User::where('id', '=', $id)
                        ->delete();
            auth()->logout();
            return view('about');
        }else{
            $error="Hibás jelszót adtál meg!";
            return view('profile.confirm_delete')->with('error_password',$error);
        }
    }

}
