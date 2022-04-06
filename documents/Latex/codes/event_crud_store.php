public function store(Request $request){
    $this->validate($request, [
        'start'=> 'required | date | after_or_equal:today',
        'end'=> 'required | date | after:start',
        'topic'=> 'required',
    ]);
    $request->user()->events()->create([
        'topic' => $request->topic,
        'description' => $request->description,
        'start'=>$request->start,
        'end'=>$request->end,
    ]);
    $user=auth()->user();
    $event=Event::where('user_id', '=', auth()->user()->id)
    ->latest('created_at')->first();
    Mail::to($user)->send(new NewEventAdded($event,$user));
    return redirect()->route('active_events');
}