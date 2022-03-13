<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Auth;

class UserController extends Controller
{
    public function login(Request $request){
        $data = $request->validate([
            'email' => 'required',
            'password' => 'required| min:6'
        ]);

        $user=User::where('email', $data['email'])->first();
        if (!$user || !Hash::check($data['password'], $user->password)) {
            return response()->json([
                'message' => 'Hibas adat!'
            ]);
        }

        $token = $user->createToken('eveldartoken')->plainTextToken;
        $loggedUser=[
            'id'=>$user['id'],
            'name'=>$user['name'],
            'email'=>$user['email'],
            'token'=>$token
        ];
        $response= [
            'user'=>$loggedUser,
            'token'=>$token
        ];

        return response($response);
    }

    public function profile(Request $request)
    {
        $user=auth()->user();

        $response=[
            'id'=>$user['id'],
            'name'=>$user['name'],
            'email'=>$user['email'],
            'created_at'=>$user['created_at']
        ];

        return response($response);
    }
    public function update(Request $request){
        $token_vali=personal_access_token::find($request['token']);
        return [
            'tokenvali'=>$token_vali
        ];
        $user=auth()->user();
        $user_id=$user['id'];
        $update_user = User::find($user_id);
        if(isset($request['password'])){
            return [
                'message'=>'Nem lehet jelszót módosítani'
            ];
        }
        $update_user->update($request->all());

        return [
            $update_user
        ];
    }

    public function updatePassword(Request $request){
        $user=auth()->user();
        $user_id=$user['id'];
        $update_user = User::find($user_id);
        $request['password']=Hash::make($request['password']);
        $update_user->update($request->all());
        auth()->user()->tokens()->delete();
        return [
            'message'=>'Sikeres jelszo modositas'
        ];
    }

    public function logout(Request $request){
        auth()->user()->tokens()->delete();
        return [
            'message'=>'Sikeres kijelentkezes'
        ];
    }
}
