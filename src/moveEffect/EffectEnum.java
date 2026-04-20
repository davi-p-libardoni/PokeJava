package moveEffect;

public enum EffectEnum {
	NONE,
	STATUS_CONDITION,   // status       //n
    STATUS_CONDITION_SELF,  //
    BUFF,               // buff em si       //
    DEBUFF,             // debuff no alvo   //
    BUFF_TARGET,        // buffa alvo           //
    DEBUFF_SELF,        // debuffa a si mesmo   //
    OMNIBUFF,			// buffa todos os stats
    WEATHER,            // muda clima       //
    CRIT,               // chance de crit aumentada     //
    HEAL,               // cura a si        //
    CONFUSE,			// confunde
    DRAIN,              // cura com base no dano causado    //
    OHKO,               // hit kill     //
    CRASH,              // toma dano se errar   //
    RECOIL,             // toma dano baseado no dano causado    //
    MULTI_HIT,          // vários hit, cada um pode errar       //
    RAMP_MULTI_HIT,     // ^^, cada hit causa mais dano q o ultimo em [chance]% (decimal, 1 = 100%) //
    COMBO,              // vários hits, apenas 1 attempt        //
    CHARGE,             // precisa carregar por 1 turno         //
    RECHARGE,           // fica 1 turno parado dps de usar      //
    FIXED_DAMAGE,       // causa dano fixo (flat,%)             //
    FLINCH,             // causa flinch no inimigo      //
    PROTECT,            // protect                      //
    PAYDAY,             // fodase n tem dinheiro        //
    BUFF_NEXT_MOVE,     // buffa proximo atk do tipo [choice]   //
    FORCE_SWITCH,       // força oponente a trocar (fodase)     //
    FLY,                // fica alto no céu por 1 turno e atk dps //
    HITS_FLY,           // pode acertar quem ta no fly          //
    TRAP,               // prende inimigo no campo, n pode trocar //
    DAMAGE_TRAP,        // ^^, causa dano por 2-5 turnos (power/%)[choice] //
    STRUGGLE,           // perde 1/4 da vida maxima como recoil //
    OUTRAGE,            // bate todo turno por 2-3 turnos e dps confunde //
    MEMENTO,            // diminui atk e spatk em 2 e morre     //
    DIVE,               // fly só q agua, 1 turno               //
    HITS_DIVE,          // bate em dive                         //
    LOW_KICK,           // mais quanto mais pesado oponente (fds) //
    PRESENT,            // move do delibird, fds
    RESET_STAT_CHANGES, // autoexplicativo
    BOUNCE,
    HITS_BOUNCE,
    TRANSFORM,
    HIDDEN_POWER,
    ENCORE,
    BATON_PASS,
    GUARANTEED_NEXT_HIT,
    DIE,
    CURSE,
    NIGHTMARE,
    DIG,
    HITS_DIG,
    SCREEN,
    BIDE,
    METRONOME,
    CONVERSION,
    TRI_ATTACK,
    CURE_CONDITION,     // choice = condição a curar
    HURT_SELF,          // degree = % max hp
    FORESIGHT,
    MAGNITUDE,
    HAZARDS,
    REMOVE_HAZARDS,
    DISABLE,            // desabilita move do oponente baseado em choice
    REST,
    DISABLE_MOVE,
    MIST,
    COUNTER,
    LEECH_SEED,
    MIMIC,
    SUBSTITUTE,
    STEAL,
    SPITE,
    REVERSAL,
    DESTINY_BOND,
    PERISH_SONG,
    ENDURE,
    ROLLOUT,
    FALSE_SWIPE,
    FURY_CUTTER,
    ATTRACT,
    SLEEP_TALK,
    RETURN,
    FRUSTRATION,
    CURE_ALL_STATUS,
    MIRROR_COAT,
    PSYCH_UP
}
