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
        $error="Hibas jelszot adtal meg!";
        return view('profile.confirm_delete')->with('error_password',$error);
    }
}