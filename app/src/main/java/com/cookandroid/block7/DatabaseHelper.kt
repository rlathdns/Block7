
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class GameCharacter(
    val id: Int? = null,  // _id, 자동 증가이므로 초기값을 null로 설정
    val imgresid: String,
    val type: String, //type
    val name: String,  // name
    val grade: Int,
    val level: Int,  // level
    val breakthrough: Int,  // breakthrough
    val attackPower: Int,  // attack_power
    val defense: Int,  // defense
    val health: Int,  // health
    val speed: Int,  // speed
    val criticalChance: Int,  // critical_chance
    val criticalDamage: Int,  // critical_damage
    val ownership: Boolean,  // ownership
    val breakthroughStone: Int,  // breakthrough_stone
    val is_pickup: Boolean
) : Serializable


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Database.db"
        private const val DATABASE_VERSION = 1
    }

    private val SQL_CREATE_CHARACTERS_TABLE = """
   CREATE TABLE characters (
       character_id INTEGER PRIMARY KEY AUTOINCREMENT,
       type STRING NOT NULL,
       name TEXT NOT NULL,
       grade INTEGER NOT NULL,
       imgresid STRING NOT NULL,
       level INTEGER NOT NULL,
       attack_power INTEGER NOT NULL,
       defense INTEGER NOT NULL,
       health INTEGER NOT NULL,
       speed INTEGER NOT NULL,
       critical_chance INTEGER NOT NULL,
       critical_damage INTEGER NOT NULL,
       breakthrough_stone INTEGER NOT NULL,
       breakthrough INTEGER NOT NULL,
        ownership INTEGER NOT NULL,
        is_pickup INTEGER NOT NULL
    );
    """

    private val SQL_CREATE_PLAYER_TABLE = """
   CREATE TABLE player (
       player_id INTEGER PRIMARY KEY AUTOINCREMENT,
       student_id STRING NOT NULL,
       currentexp INT NOT NULL DEFAULT 0,
       level INT NOT NULL DEFAULT 1,
       gold INT NOT NULL DEFAULT 1000,
       gems INT NOT NULL DEFAULT 1000,
       expbook INT NOT NULL DEFAULT 0,
       stoneofcontract INT NOT NULL DEFAULT 0,
       contract_try_count INT NOT NULL DEFAULT 0
    );
    """

    private val SQL_CREATE_PULL_TABLE = """
    CREATE TABLE pulls (
    player_id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id STRING NOT NULL,
    pull_item STRING NOT NULL,
    pull_time STRING NOT NULL,
    count INT NOT NULL
    );
    """


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CHARACTERS_TABLE)
        db.execSQL(SQL_CREATE_PLAYER_TABLE)
        db.execSQL(SQL_CREATE_PULL_TABLE)

        // 기본 캐릭터 정보 및 능력치 삽입
        val characters = listOf(
            GameCharacter(null, "character_rus_zoom_in", "c++", "루스", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_lucy_zoom_in", "c++", "루시", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_glaceon_zoom_in", "c++","글레이시아", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_nirvana_zoom_in", "c++","니르바나", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_dea_zoom_in", "c++","데아", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_baal_zoom_in", "c++","바알", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_blondie_zoom_in", "c++","블론디", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_victor_zoom_in", "c++","빅터", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_acua_zoom_in", "c++","아쿠아", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_ene_zoom_in", "c++","에네", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_kalla_zoom_in", "c++","칼라", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_claire_zoom_in", "c++","클레어", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_peulla_zoom_in", "c++","플라", 5, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_nox_zoom_in", "c++","녹스", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_nixy_zoom_in", "c++","닉시", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_rosalyn_zoom_in", "c++","로잘린", 4, 0, 0, 100, 100, 2000, 10, 30, 50, true, 0,false),
            GameCharacter(null, "character_mos_zoom_in", "c++","모스", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_bahamut_zoom_in", "c++","바하무트", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_bailey_zoom_in", "c++","베일리", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_bella_zoom_in", "c++","벨라", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_buer_zoom_in", "c++","뷔에르", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_bianca_zoom_in", "c++","비앙카", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_sally_zoom_in", "c++","셀리", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_solar_zoom_in", "c++","솔라", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_iris_zoom_in", "c++","아이리스", 4, 0, 0, 100, 100, 2000, 10, 30, 50, true, 0,true),
            GameCharacter(null, "character_eileen_zoom_in", "c++","아일린", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_angelica_zoom_in", "c++","안젤리카", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_aaron_zoom_in", "c++","아론", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_yeonwol_zoom_in", "c++","연월", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_aurora_zoom_in", "c++","오로라", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_eden_zoom_in", "c++","이든", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_isabelle_zoom_in", "c++","이자벨", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_jane_zoom_in", "c++","제인", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_zepeto_zoom_in", "c++","제페토", 4, 0, 0, 100, 100, 2000, 10, 30, 50, true, 0,false),
            GameCharacter(null, "character_felix_zoom_in", "c++","펠릭스", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_plato_zoom_in", "c++","플라토", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_flos_zoom_in", "c++","플로스", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,true),
            GameCharacter(null, "character_philis_zoom_in", "c++","필리스", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false),
            GameCharacter(null, "character_hanna_zoom_in", "c++","한나", 4, 0, 0, 100, 100, 2000, 10, 30, 50, false, 0,false)
        )


        for (character in characters) {
            val contentValues = ContentValues().apply {
                put("character_id", character.id)
                put("name", character.name)
                put("imgresid", character.imgresid)
                put("grade", character.grade)
                put("type", character.type)
                put("level", character.level)
                put("breakthrough", character.breakthrough)
                put("attack_power", character.attackPower)
                put("defense", character.defense)
                put("health", character.health)
                put("speed", character.speed)
                put("critical_chance", character.criticalChance)
                put("critical_damage", character.criticalDamage)
                put("ownership", if (character.ownership) 1 else 0)
                put("breakthrough_stone", character.breakthroughStone)
                put("is_pickup", if (character.is_pickup) 1 else 0)
            }
            db.insert("characters", null, contentValues)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS characters")
        onCreate(db)
    }

    fun getAllCharacters(): List<GameCharacter> {
        val characters = mutableListOf<GameCharacter>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM characters", null)

        if (cursor.moveToFirst()) {
            do {
                val character = GameCharacter(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("character_id")),
                    imgresid = cursor.getString(cursor.getColumnIndexOrThrow("imgresid")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    grade = cursor.getInt(cursor.getColumnIndexOrThrow("grade")),
                    type = cursor.getString(cursor.getColumnIndexOrThrow("type")),
                    level = cursor.getInt(cursor.getColumnIndexOrThrow("level")),
                    breakthrough = cursor.getInt(cursor.getColumnIndexOrThrow("breakthrough")),
                    attackPower = cursor.getInt(cursor.getColumnIndexOrThrow("attack_power")),
                    defense = cursor.getInt(cursor.getColumnIndexOrThrow("defense")),
                    health = cursor.getInt(cursor.getColumnIndexOrThrow("health")),
                    speed = cursor.getInt(cursor.getColumnIndexOrThrow("speed")),
                    criticalChance = cursor.getInt(cursor.getColumnIndexOrThrow("critical_chance")),
                    criticalDamage = cursor.getInt(cursor.getColumnIndexOrThrow("critical_damage")),
                    ownership = cursor.getInt(cursor.getColumnIndexOrThrow("ownership")) > 0,
                    breakthroughStone = cursor.getInt(cursor.getColumnIndexOrThrow("breakthrough_stone")),
                    is_pickup = cursor.getInt(cursor.getColumnIndexOrThrow("is_pickup")) > 0,

                    )
                characters.add(character)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return characters
    }

    fun getPlayerInfo(studentId: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM player WHERE student_id = ?", arrayOf(studentId))
    }

    fun insertNewPlayer(studentId: String) {
        val db = this.writableDatabase

        val cursor = db.rawQuery("SELECT * FROM player WHERE student_id = ?", arrayOf(studentId))
        val isExisting = cursor.moveToFirst()
        cursor.close()

        if (!isExisting) {
            val values = ContentValues()

            values.put("student_id", studentId)
            db.insert("player", null, values)
        }
        db.close()
    }

    fun updateCharacterOwnershipOrBreakthrough(characterName: String) {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM characters WHERE name = ?", arrayOf(characterName))

        if (cursor.moveToFirst()) {
            val ownership = cursor.getInt(cursor.getColumnIndexOrThrow("ownership")) > 0
            val breakthrough = cursor.getInt(cursor.getColumnIndexOrThrow("breakthrough"))
            val breakthroughStone =
                cursor.getInt(cursor.getColumnIndexOrThrow("breakthrough_stone"))
            val totalBreakthrough = breakthrough + breakthroughStone

            if (!ownership) {
                val contentValues = ContentValues().apply {
                    put("ownership", 1)
                }
                db.update("characters", contentValues, "name = ?", arrayOf(characterName))
            } else if (totalBreakthrough < 5) {
                val contentValues = ContentValues().apply {
                    put("breakthrough_stone", breakthroughStone + 1)
                }
                db.update("characters", contentValues, "name = ?", arrayOf(characterName))
            } else {
                addStoneOfContract(6)
            }
        }
        cursor.close()
    }

    fun addStoneOfContract(amount: Int) {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT stoneofcontract FROM player", null)
        if (cursor.moveToFirst()) {
            val currentStone = cursor.getInt(cursor.getColumnIndexOrThrow("stoneofcontract"))
            val contentValues = ContentValues().apply {
                put("stoneofcontract", currentStone + amount)
            }
            db.update("player", contentValues, null, null)
        }
        cursor.close()
    }

    fun getPickupCharacters(): List<GameCharacter> {
        val characters = mutableListOf<GameCharacter>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM characters WHERE is_pickup = 1", null)

        if (cursor.moveToFirst()) {
            do {
                val character = GameCharacter(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("character_id")),
                    imgresid = cursor.getString(cursor.getColumnIndexOrThrow("imgresid")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    grade = cursor.getInt(cursor.getColumnIndexOrThrow("grade")),
                    type = cursor.getString(cursor.getColumnIndexOrThrow("type")),
                    level = cursor.getInt(cursor.getColumnIndexOrThrow("level")),
                    breakthrough = cursor.getInt(cursor.getColumnIndexOrThrow("breakthrough")),
                    attackPower = cursor.getInt(cursor.getColumnIndexOrThrow("attack_power")),
                    defense = cursor.getInt(cursor.getColumnIndexOrThrow("defense")),
                    health = cursor.getInt(cursor.getColumnIndexOrThrow("health")),
                    speed = cursor.getInt(cursor.getColumnIndexOrThrow("speed")),
                    criticalChance = cursor.getInt(cursor.getColumnIndexOrThrow("critical_chance")),
                    criticalDamage = cursor.getInt(cursor.getColumnIndexOrThrow("critical_damage")),
                    ownership = cursor.getInt(cursor.getColumnIndexOrThrow("ownership")) > 0,
                    breakthroughStone = cursor.getInt(cursor.getColumnIndexOrThrow("breakthrough_stone")),
                    is_pickup = cursor.getInt(cursor.getColumnIndexOrThrow("is_pickup")) > 0,
                )
                characters.add(character)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return characters
    }

    fun addExpBook(studentId: String, amount: Int) {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT expbook FROM player WHERE student_id = ?", arrayOf(studentId))
        if (cursor.moveToFirst()) {
            val currentExpBook = cursor.getInt(cursor.getColumnIndexOrThrow("expbook"))
            val contentValues = ContentValues().apply {
                put("expbook", currentExpBook + amount)
            }
            db.update("player", contentValues, "student_id=?", arrayOf(studentId))
        }
        cursor.close()
    }



    fun getNonPickupCharacters(star: Int): List<GameCharacter> {
        // 픽업이 아닌 캐릭터들을 반환
        return getAllCharacters().filter { it.grade == star && !it.is_pickup }
    }

    fun getContractTryCount(studentId: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT contract_try_count FROM player WHERE student_id = ?", arrayOf(studentId))
        var tryCount = 0

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("contract_try_count")
            if (columnIndex == -1) {
                throw IllegalArgumentException("contract_try_count 컬럼이 존재하지 않습니다.")
            }
            tryCount = cursor.getInt(columnIndex)
        }

        cursor.close()

        return tryCount
    }

    fun updateContractTryCount(studentId: String) {
        val db = this.writableDatabase

        val currentCount = getContractTryCount(studentId)

        val updatedCount = if (currentCount == 70) 0 else currentCount + 1

        val contentValues = ContentValues()
        contentValues.put("contract_try_count", updatedCount)

        db.update("player", contentValues, "student_id = ?", arrayOf(studentId))
        db.close()
    }

    fun pullRecord(studentId : String, pullItem : String, count : Int){
        val db = this.writableDatabase

        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

        val cursor = db.rawQuery("SELECT pull_item FROM pulls WHERE student_id = ?", arrayOf(studentId))
        val contentValues = ContentValues().apply {
            put("pull_item", pullItem)
            put("count", count)
            put("pull_time", currentTime)
        }
        if (cursor.moveToFirst()) {
            db.update("pulls", contentValues, "student_id=?", arrayOf(studentId))
        } else {
            contentValues.put("student_id", studentId)
            db.insert("pulls", null, contentValues)
        }
        cursor.close()
    }

    fun getAllPullRecord(studentId: String): List<Map<String, Any>> {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM pulls WHERE student_id = ?", arrayOf(studentId))
        val records = mutableListOf<Map<String, Any>>()

        while (cursor.moveToNext()) {
            val record = mutableMapOf<String, Any>()

            val pullItemIndex = cursor.getColumnIndex("pull_item")
            val pullTimeIndex = cursor.getColumnIndex("pull_time")
            val countIndex = cursor.getColumnIndex("count")

            if (pullItemIndex != -1) record["pull_item"] = cursor.getString(pullItemIndex)
            if (pullTimeIndex != -1) record["pull_time"] = cursor.getString(pullTimeIndex)
            if (countIndex != -1) record["count"] = cursor.getInt(countIndex)

            records.add(record)
        }
        cursor.close()
        return records
    }

    fun getRecentPullRecord(studentId: String, getCount: Int): List<Map<String, Any>> {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM pulls WHERE student_id = ? ORDER BY pull_time DESC LIMIT ?", arrayOf(studentId, getCount.toString()))
        val records = mutableListOf<Map<String, Any>>()

        val pullItemIndex = cursor.getColumnIndex("pull_item")
        val pullTimeIndex = cursor.getColumnIndex("pull_time")
        val countIndex = cursor.getColumnIndex("count")

        while (cursor.moveToNext()) {
            val record = mutableMapOf<String, Any>()

            if (pullItemIndex != -1) record["pull_item"] = cursor.getString(pullItemIndex)
            if (pullTimeIndex != -1) record["pull_time"] = cursor.getString(pullTimeIndex)
            if (countIndex != -1) record["count"] = cursor.getInt(countIndex)

            records.add(record)
        }
        cursor.close()
        return records
    }


}
