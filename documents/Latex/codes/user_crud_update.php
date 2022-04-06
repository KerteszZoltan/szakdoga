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