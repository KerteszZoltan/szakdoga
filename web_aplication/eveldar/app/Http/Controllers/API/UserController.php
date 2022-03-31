<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\DB;

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

    public function checkToken(Request $request){
        $usedToken=$request['id'];
        $userId=$request['id'];
        $storedToken=DB::table('personal_access_tokens')->where('tokenable_id', $userId)->value('tokenable_id');
        if($storedToken == $userId){
            return [
                'id'=> $usedToken
            ];
        }
        else{
            return [
                'id'=>null
            ];
        }
    }

    public function profile(Request $request)
    {
        $user=auth()->user();

        $response=[
            'user'=>$user
        ];

        return response($response);
    }
    public function update(Request $request){
        $user=auth()->user();
        $user_id=$user['id'];
        $update_user = User::find($user_id);
        if(isset($request['password'])){
            return [
                'message'=>'Nem lehet jelszót módosítani'
            ];
        }
        $update_user->update($request->all());
        $token = $request->bearerToken();

        $updated_user=[
            'id'=>$update_user['id'],
            'name'=>$update_user['name'],
            'email'=>$update_user['email'],
            'token'=>$token
        ];

        return [
            'user'=>$updated_user,
            'token'=>$token
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
