<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

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

    public function confirm_delete(){
        return view('profile.confirm_delete');
    }

    public function delete(Request $request) {
        $this-> validate($request,[
            'password'=>'required|confirmed',
        ]);

        $id=$request->input('id');
        $password=Hash::make($request->password);
        $user=User::find($id);
        $user=User::where('id', '=', $id)
                    ->where('password','=',$password)
                    ->delete('id','=',$id);
        $check=User::where('id','=',$id)
                    ->count();
        if($check>0){
            $error="Hibás jelszót adtál meg!";
            return view('profile.confirm_delete')->with('error_password',$error);
        }else{
            auth()->logout();
            return view('about');
        }
    }
}
