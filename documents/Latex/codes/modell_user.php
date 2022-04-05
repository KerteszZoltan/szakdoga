class User extends Authenticatable{
    use HasApiTokens, HasFactory, Notifiable;
    protected $fillable = [
        'name',
        'email',
        'password',
    ];
    protected $hidden = [
        'password',
        'remember_token',
        'api_token',
    ];
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];
    public function events(){
        return $this->hasMany(Event::class);
    }
}