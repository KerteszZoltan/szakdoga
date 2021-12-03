<?php

namespace App\Http\Controllers;

use App\Models\Event;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class EventController extends Controller
{
    public function __construct()
    {
        $this->middleware(['auth']);
    }

    public function index(){
        return view('events.create_event');
    }

    public function active_events()
    {

        $events = DB::table('events')
                                    ->where('user_id', auth()->user()->id)
                                    ->orderByRaw('start DESC')
                                    ->paginate(2);

        //$events=Event::get();
        return view('events.active_event',[
            'events' => $events
        ]);
    }

    public function store(Request $request)
    {
        $this->validate($request, [
            'start'=> 'required',
            'end'=> 'required',
            'topic'=> 'required',
        ]);

        $request->user()->events()->create([
            'topic' => $request->topic,
            'description' => $request->description,
            'start'=>$request->start,
            'end'=>$request->end,
        ]);

        return EventController::active_events();
    }
}
