public function active_events(){
    $events = Event::where('user_id', auth()->user()->id)
                     ->where('end', '>', date('Y-m-d H:i:sa') )
                     ->where('complete', '=', '0')
                     ->orderByRaw('end ASC')
                     ->paginate(2);
    return view('events.event',[
        'events' => $events,
        'type'=>'active',
    ]);
}