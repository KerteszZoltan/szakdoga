public function up()
    {
        Schema::create('events', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->string('topic');
            $table->text('description')->nullable();
            $table->dateTime('start');
            $table->dateTime('end');
            $table->boolean('complete')->default('0');
            $table->timestamps();
        });
    }
    public function down()
    {
        Schema::dropIfExists('events');
    }