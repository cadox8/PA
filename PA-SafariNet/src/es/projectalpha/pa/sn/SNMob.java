package es.projectalpha.pa.sn;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.sn.files.Files;
import es.projectalpha.pa.sn.utils.ColorUtils;
import es.projectalpha.pa.sn.utils.MobUtils;
import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SNMob {

    @Getter private final Player player;
    @Getter private final Entity entity;

    private final Files files = new Files();
    private final ColorUtils color = new ColorUtils();
    private final MobUtils mu = new MobUtils();

    public SNMob(Entity entity, Player player){
        this.entity = entity;
        this.player = player;
    }

    public SNMob(Player player){
        this.player = player;
        this.entity = null;
    }

    public short getEntityID(){
        return (short)entity.getEntityId();
    }

    public EntityType getEntityType(){
        return entity.getType();
    }

    public String getCustomName(){
        return entity.getCustomName() != null ? entity.getCustomName() : "";
    }

    public double getHealth(){
        return getLivingEntity().getHealth();
    }

    public int getAgeAnimals(){
        Animals a;
        if (!mu.isAnimal(entity)) return -1;
        a = (Animals)entity;
        return a.getAge();
    }

    public boolean isBaby(){
        Zombie z;
        if (!mu.isZombie(entity) || !mu.isZombieVillager(entity) || !mu.isPigZombie(entity)) return false;
        z = (Zombie)entity;
        return z.isBaby();
    }

    public Villager.Profession getVillagerZombieProfession(){
        ZombieVillager zv;
        if (!mu.isZombieVillager(entity)) return Villager.Profession.BLACKSMITH;
        zv = (ZombieVillager)entity;
        return zv.getVillagerProfession();
    }

    public DyeColor getColor(){
        Sheep s;
        if (!mu.isSheep(entity)) return null;
        s = (Sheep)entity;
        return s.getColor();
    }

    public Villager.Profession getVillagerProfession(){
        Villager v;
        if (!mu.isVillager(entity)) return Villager.Profession.BLACKSMITH;
        v = (Villager)entity;
        return v.getProfession();
    }

    public String getVillagerMerchant(){
        Villager v;
        if (!mu.isVillager(entity)) return "";
        v = (Villager)entity;
        return v.getRecipes().toString(); //Método temporal, creando uno mejor
    }

    //AbstractHorse
    public double getJumpStrength(){
        AbstractHorse h;
        if (!isTamed()) return 1;
        if (!mu.isHorse(entity) || !mu.isDonkey(entity) || !mu.isMule(entity) || !mu.isZombieHorse(entity) || !mu.isSkeletonHorse(entity)) return 0;
        h = (AbstractHorse)entity;
        return h.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue();
    }

    public int getDomesticationHorse(){
        Horse h;
        if (!mu.isHorse(entity) || !mu.isDonkey(entity) || !mu.isMule(entity) || !mu.isZombieHorse(entity) || !mu.isSkeletonHorse(entity)) return 0;
        h = (Horse)entity;
        return h.getDomestication();
    }

    public boolean isTamed(){
        Horse h;
        if (!mu.isHorse(entity) || !mu.isDonkey(entity) || !mu.isMule(entity) || !mu.isZombieHorse(entity) || !mu.isSkeletonHorse(entity)) return false;
        h = (Horse)entity;
        return h.isTamed();
    }

    //Horse
    public Horse.Color getHorseColor(){
        Horse h;
        if (!mu.isHorse(entity)) return Horse.Color.BLACK;
        h = (Horse)entity;
        return h.getColor();
    }

    public Horse.Style getHorseStyle(){
        Horse h;
        if (!mu.isHorse(entity)) return Horse.Style.NONE;
        h = (Horse)entity;
        return h.getStyle();
    }

    //ChestedHorse
    public boolean isCarryingChest(){
        ChestedHorse ch;
        if (!mu.isDonkey(entity) || !mu.isMule(entity) || !mu.isLlama(entity)) return false;
        ch = (ChestedHorse)entity;
        return ch.isCarryingChest();
    }

    //Llama
    public Llama.Color getLlamaColor(){
        Llama l;
        if (!mu.isLlama(entity)) return Llama.Color.BROWN;
        l = (Llama)entity;
        return l.getColor();
    }

    public int getLlamaStrength(){
        Llama l;
        if (!mu.isLlama(entity)) return 0;
        l = (Llama)entity;
        return l.getStrength();
    }

    public MerchantRecipe[] getRecipes(){
        Villager v;
        if (!mu.isVillager(entity)) return null;
        v = (Villager)entity;
        return v.getRecipes().toArray(new MerchantRecipe[v.getRecipes().size()]);
    }

    public boolean isChargedCreeper(){
        Creeper c;
        if (!mu.isCreeper(entity)) return false;
        c = (Creeper)entity;
        return c.isPowered();
    }

    public Rabbit.Type getRabbitType(){
        Rabbit r;
        if (!mu.isRabbit(entity)) return Rabbit.Type.BLACK;
        r = (Rabbit)entity;
        return r.getRabbitType();
    }

    //Parrot
    public Parrot.Variant getVariant() {
        Parrot p;
        if (!mu.isParrot(entity)) return null;
        p = (Parrot) entity;
        return p.getVariant();
    }

    //Shulker
    public DyeColor getShulkerColor() {
        Shulker s;
        if (!mu.isSheep(entity)) return null;
        s = (Shulker)entity;
        return s.getColor();
    }

    //Utils
    private LivingEntity getLivingEntity(){
        return (LivingEntity)entity;
    }

    //Data
    public ItemStack getMobEgg(){
        return new ItemMaker(Material.MONSTER_EGG).setDisplayName("Spawn " + color.getRandomColor() + WordUtils.capitalizeFully(getEntityType().toString().toLowerCase())).setAmount(1).setDurability(getEntityID()).setLores((files.getMobsCount(player) - 1) + "", getEntityType().toString()).build();
    }

    //Methods
    public void writeConfig(){
        HashMap<String, String> settings = new HashMap<>();

        //Default
        settings.put("mobType", getEntityType().toString());
        settings.put("name", getCustomName());
        settings.put("health", getHealth() + "");
        settings.put("owner", player.getName());

        //Mobs
        if (mu.isAnimal(entity)) settings.put("age", getAgeAnimals() + "");
        if (mu.isSheep(entity)) settings.put("color", getColor().toString());
        if (mu.isVillager(entity)) settings.put("profession", getVillagerProfession().toString());
        if (mu.isVillager(entity)) settings.put("recipes", getVillagerMerchant());
        if (mu.isZombie(entity) || mu.isZombieVillager(entity) || mu.isPigZombie(entity)) settings.put("baby", String.valueOf(isBaby()));
        if (mu.isZombieVillager(entity)) settings.put("professionZombie", getVillagerZombieProfession().toString());
        //AbstractHorse
        if (mu.isHorse(entity)) settings.put("jump", getJumpStrength() + "");
        if (mu.isHorse(entity) || mu.isDonkey(entity) || mu.isMule(entity) || mu.isLlama(entity)) settings.put("tamed", String.valueOf(isTamed()));
        if (mu.isHorse(entity) || mu.isDonkey(entity) || mu.isMule(entity) || mu.isLlama(entity)) settings.put("domestication", getDomesticationHorse() + "");
        //Horse
        if (mu.isHorse(entity)) settings.put("horseColor", getHorseColor().toString());
        if (mu.isHorse(entity)) settings.put("horseStyle", getHorseStyle().toString());
        //ChestedHorse
        if (mu.isDonkey(entity) || mu.isMule(entity) || mu.isLlama(entity)) settings.put("chest", String.valueOf(isCarryingChest()));
        //Llama
        if (mu.isLlama(entity)) settings.put("llamaColor", getLlamaColor().toString());
        if (mu.isLlama(entity)) settings.put("llamaStrength", getLlamaStrength() + "");
        //Villager
        if (mu.isVillager(entity)) settings.put("recipes", getRecipes().toString());
        //Creeper
        if (mu.isCreeper(entity)) settings.put("powered", String.valueOf(isChargedCreeper()));
        //Rabbit
        if (mu.isRabbit(entity)) settings.put("rabbitType", getRabbitType().toString());
        //Parrot
        if (mu.isParrot(entity)) settings.put("parrotType", getVariant().toString());
        if (mu.isParrot(entity)) settings.put("tamed", String.valueOf(isTamed()));

        //
        String path = player.getName() + "_" + files.getMobsCount(player) + ".";

        settings.keySet().forEach(t -> files.getMobs().set(path + t, settings.get(t)));

        files.addMob(player);
        files.saveFiles();
    }

    public void givePlayerEgg(){
        player.getInventory().addItem(getMobEgg());
    }

    private HashMap<String, String> getConfig(int id){
        HashMap<String, String> settings = new HashMap<>();
        String path = player.getName() + "_" + id;

        if (!files.getMobs().contains(path)) return settings;

        settings.put("mobType", files.getMobs().getString(path + ".mobType"));
        settings.put("name", files.getMobs().getString(path + ".name"));
        settings.put("health", files.getMobs().getString(path + ".health"));
        settings.put("owner", files.getMobs().getString(path + ".owner"));

        if (files.getMobs().contains(path + ".age")) settings.put("age", files.getMobs().getString(path + ".age"));
        if (files.getMobs().contains(path + ".color")) settings.put("color", files.getMobs().getString(path + ".color"));
        if (files.getMobs().contains(path + ".profession")) settings.put("profession", files.getMobs().getString(path + ".profession"));
        if (files.getMobs().contains(path + ".recipes")) settings.put("recipes", files.getMobs().getString(path + ".recipes"));
        if (files.getMobs().contains(path + ".baby")) settings.put("baby", files.getMobs().getString(path + ".baby"));
        if (files.getMobs().contains(path + ".professionZombie")) settings.put("professionZombie", files.getMobs().getString(path + ".professionZombie"));
        if (files.getMobs().contains(path + ".jump")) settings.put("jump", files.getMobs().getString(path + ".jump"));
        if (files.getMobs().contains(path + ".tamed")) settings.put("tamed", files.getMobs().getString(path + ".tamed"));
        if (files.getMobs().contains(path + ".domestication")) settings.put("domestication", files.getMobs().getString((path + ".domestication")));
        if (files.getMobs().contains(path + ".horseColor")) settings.put("horseColor", files.getMobs().getString(path + ".horseColor"));
        if (files.getMobs().contains(path + ".horseStyle")) settings.put("horseStyle", files.getMobs().getString(path + ".horseStyle"));
        if (files.getMobs().contains(path + ".chest")) settings.put("chest", files.getMobs().getString(path + ".chest"));
        if (files.getMobs().contains(path + ".llamaColor")) settings.put("llamaColor", files.getMobs().getString(path + ".llamaColor"));
        if (files.getMobs().contains(path + ".llamaStrength")) settings.put("llamaStrength", files.getMobs().getString(path + ".llamaStrength"));
        if (files.getMobs().contains(path + ".recipes")) settings.put("recipes", files.getMobs().getString(path + ".recipes"));
        if (files.getMobs().contains(path + ".powered")) settings.put("powered", files.getMobs().getString(path + ".powered"));
        if (files.getMobs().contains(path + ".rabbitType")) settings.put("rabbitType", files.getMobs().getString(path + ".rabbitType"));
        if (files.getMobs().contains(path + ".parrotType")) settings.put("parrotType", files.getMobs().getString(path + ".parrotType"));
        if (files.getMobs().contains(path + ".shul")) settings.put("shul", files.getMobs().getString(path + ".shul"));

        return settings;
    }

    public boolean isOwner(int id){
        String path = player.getName() + "_" + id;
        return files.getMobs().contains(path);
    }

    public void spawnMob(int id, String entityTypeS){
        HashMap<String, String> settings = getConfig(id);

        String name = settings.keySet().contains("name") ? settings.get("name") : "";
        double health = settings.keySet().contains("health") ? Double.valueOf(settings.get("health")) : 0;
        int age = settings.keySet().contains("age") ? Integer.parseInt(settings.get("age")) : 0;
        DyeColor color = settings.keySet().contains("color") ? DyeColor.valueOf(settings.get("color")) : DyeColor.WHITE;
        Villager.Profession profession = settings.keySet().contains("profession") ?  Villager.Profession.valueOf(settings.get("profession")) : Villager.Profession.BLACKSMITH;
        List<MerchantRecipe> recipes = settings.keySet().contains("recipes") ?  new ArrayList<>() : new ArrayList<>();
        boolean isBaby = settings.keySet().contains("baby") ? Boolean.valueOf(settings.get("baby")) : false;
        Villager.Profession professionZombie = settings.keySet().contains("professionZombie") ?  Villager.Profession.valueOf(settings.get("professionZombie")) : Villager.Profession.BLACKSMITH;
        double JumpStrenght = settings.keySet().contains("jump") ? Double.parseDouble(settings.get("jump")) : new Random().nextInt(5) + 1;
        int domestication = settings.keySet().contains("domestication") ? Integer.parseInt(settings.get("domestication")) : 0;
        boolean isTamed = Boolean.parseBoolean(settings.get("tamed"));
        Horse.Color horseColor = settings.keySet().contains("horseColor") ? Horse.Color.valueOf(settings.get("horseColor")) : Horse.Color.BLACK;
        Horse.Style horseStyle = settings.keySet().contains("horseStyle") ? Horse.Style.valueOf(settings.get("horseStyle")) : Horse.Style.NONE;
        boolean hasChest = settings.keySet().contains("chest") ? Boolean.valueOf(settings.get("chest")) : false;
        Llama.Color llamaColor = settings.keySet().contains("llamaColor") ? Llama.Color.valueOf(settings.get("llamaColor")) : Llama.Color.BROWN;
        int llamaStrength = settings.keySet().contains("llamaStrength") ? Integer.parseInt(settings.get("llamaStrength")) : 0;
        boolean powered = settings.keySet().contains("powered") ? Boolean.valueOf(settings.get("powered")) : false;
        Rabbit.Type rabbitType = settings.keySet().contains("rabbitType") ? Rabbit.Type.valueOf(settings.get("rabbitType")) : Rabbit.Type.BLACK;
        Parrot.Variant parrotType = settings.keySet().contains("parrotType") ? Parrot.Variant.valueOf(settings.get("parrotType")) : Parrot.Variant.BLUE;
        DyeColor shul = settings.keySet().contains("shul") ? DyeColor.valueOf(settings.get("shul")) : DyeColor.PURPLE;

        switch (EntityType.valueOf(entityTypeS)){
            case SHEEP:
                Sheep s = player.getWorld().spawn(player.getLocation(), Sheep.class);
                s.setCustomName(name);
                s.setCustomNameVisible(true);
                s.setAge(age);
                s.setColor(color);
                s.setMaxHealth(health);
                s.setHealth(health);
                break;
            case PIG:
                Pig pig = player.getWorld().spawn(player.getLocation(), Pig.class);
                pig.setCustomName(name);
                pig.setCustomNameVisible(true);
                pig.setAge(age);
                pig.setMaxHealth(health);
                pig.setHealth(health);
                break;
            case OCELOT:
                Ocelot o = player.getWorld().spawn(player.getLocation(), Ocelot.class);
                o.setCustomName(name);
                o.setCustomNameVisible(true);
                o.setAge(age);
                o.setMaxHealth(health);
                o.setHealth(health);
                break;
            case WOLF:
                Wolf w = player.getWorld().spawn(player.getLocation(), Wolf.class);
                w.setCustomName(name);
                w.setCustomNameVisible(true);
                w.setAge(age);
                w.setMaxHealth(health);
                w.setHealth(health);
                break;
            case GUARDIAN:
                Guardian gu = player.getWorld().spawn(player.getLocation(), Guardian.class);
                gu.setCustomName(name);
                gu.setCustomNameVisible(true);
                gu.setMaxHealth(health);
                gu.setHealth(health);
                break;
            case COW:
                Cow c = player.getWorld().spawn(player.getLocation(), Cow.class);
                c.setCustomName(name);
                c.setCustomNameVisible(true);
                c.setAge(age);
                c.setMaxHealth(health);
                c.setHealth(health);
                break;
            case MUSHROOM_COW:
                MushroomCow mc = player.getWorld().spawn(player.getLocation(), MushroomCow.class);
                mc.setCustomName(name);
                mc.setCustomNameVisible(true);
                mc.setAge(age);
                mc.setMaxHealth(health);
                mc.setHealth(health);
                break;
            case CHICKEN:
                Chicken ch = player.getWorld().spawn(player.getLocation(), Chicken.class);
                ch.setCustomName(name);
                ch.setCustomNameVisible(true);
                ch.setAge(age);
                ch.setMaxHealth(health);
                ch.setHealth(health);
                break;
            case VILLAGER:
                Villager v = player.getWorld().spawn(player.getLocation(), Villager.class);
                v.setCustomName(name);
                v.setCustomNameVisible(true);
                v.setAge(age);
                v.setProfession(profession);
                v.setMaxHealth(health);
                v.setHealth(health);
                break;
            //Horse
            case HORSE:
                Horse h = player.getWorld().spawn(player.getLocation(), Horse.class);
                h.setAge(age);
                h.setJumpStrength(JumpStrenght);
                h.setColor(horseColor);
                h.setStyle(horseStyle);
                h.setMaxHealth(health);
                h.setHealth(health);
                h.setDomestication(domestication);
                h.setTamed(isTamed);
                break;
            case DONKEY:
                Donkey d = player.getWorld().spawn(player.getLocation(), Donkey.class);
                d.setCustomName(name);
                d.setCustomNameVisible(true);
                d.setAge(age);
                d.setCarryingChest(hasChest);
                d.setMaxHealth(health);
                d.setHealth(health);
                d.setJumpStrength(JumpStrenght);
                d.setDomestication(domestication);
                d.setTamed(isTamed);
                break;
            case MULE:
                Mule m = player.getWorld().spawn(player.getLocation(), Mule.class);
                m.setCustomName(name);
                m.setCustomNameVisible(true);
                m.setAge(age);
                m.setCarryingChest(hasChest);
                m.setMaxHealth(health);
                m.setHealth(health);
                m.setJumpStrength(JumpStrenght);
                m.setDomestication(domestication);
                m.setTamed(isTamed);
                break;
            case LLAMA:
                Llama ll = player.getWorld().spawn(player.getLocation(), Llama.class);
                ll.setCustomName(name);
                ll.setCustomNameVisible(true);
                ll.setColor(llamaColor);
                ll.setCarryingChest(hasChest);
                ll.setStrength(llamaStrength);
                ll.setMaxHealth(health);
                ll.setHealth(health);
                ll.setJumpStrength(JumpStrenght);
                ll.setDomestication(domestication);
                ll.setTamed(isTamed);
                break;
            //Zombie
            case ZOMBIE:
                Zombie z = player.getWorld().spawn(player.getLocation(), Zombie.class);
                if (isBaby) z.setBaby(true);
                z.setCustomName(name);
                z.setCustomNameVisible(true);
                z.setMaxHealth(health);
                z.setHealth(health);
                break;
            case PIG_ZOMBIE:
                PigZombie pz = player.getWorld().spawn(player.getLocation(), PigZombie.class);
                if (isBaby) pz.setBaby(true);
                pz.setCustomName(name);
                pz.setCustomNameVisible(true);
                pz.setMaxHealth(health);
                pz.setHealth(health);
                break;
            case ZOMBIE_VILLAGER:
                ZombieVillager zv = player.getWorld().spawn(player.getLocation(), ZombieVillager.class);
                if (isBaby) zv.setBaby(true);
                zv.setCustomName(name);
                zv.setCustomNameVisible(true);
                zv.setVillagerProfession(professionZombie);
                zv.setMaxHealth(health);
                zv.setHealth(health);
                break;
            case ZOMBIE_HORSE:
                ZombieHorse zh = player.getWorld().spawn(player.getLocation(), ZombieHorse.class);
                zh.setCustomName(name);
                zh.setCustomNameVisible(true);
                zh.setMaxHealth(health);
                zh.setHealth(health);
                zh.setJumpStrength(JumpStrenght);
                break;
            case SKELETON_HORSE:
                SkeletonHorse sh = player.getWorld().spawn(player.getLocation(), SkeletonHorse.class);
                sh.setCustomName(name);
                sh.setCustomNameVisible(true);
                sh.setMaxHealth(health);
                sh.setHealth(health);
                sh.setJumpStrength(JumpStrenght);
                break;
            case SKELETON:
                Skeleton sk = player.getWorld().spawn(player.getLocation(), Skeleton.class);
                sk.setCustomName(name);
                sk.setCustomNameVisible(true);
                sk.setMaxHealth(health);
                sk.setHealth(health);
                break;
            case WITHER_SKELETON:
                WitherSkeleton ws = player.getWorld().spawn(player.getLocation(), WitherSkeleton.class);
                ws.setCustomName(name);
                ws.setCustomNameVisible(true);
                ws.setMaxHealth(health);
                ws.setHealth(health);
                break;
            case CREEPER:
                Creeper cr = player.getWorld().spawn(player.getLocation(), Creeper.class);
                cr.setCustomName(name);
                cr.setCustomNameVisible(true);
                cr.setMaxHealth(health);
                cr.setHealth(health);
                cr.setPowered(powered);
                break;
            case SPIDER:
                Spider sp = player.getWorld().spawn(player.getLocation(), Spider.class);
                sp.setCustomName(name);
                sp.setCustomNameVisible(true);
                sp.setMaxHealth(health);
                sp.setHealth(health);
                break;
            case CAVE_SPIDER:
                CaveSpider cs = player.getWorld().spawn(player.getLocation(), CaveSpider.class);
                cs.setCustomName(name);
                cs.setCustomNameVisible(true);
                cs.setMaxHealth(health);
                cs.setHealth(health);
                break;
            case BLAZE:
                Blaze b = player.getWorld().spawn(player.getLocation(), Blaze.class);
                b.setCustomName(name);
                b.setCustomNameVisible(true);
                b.setMaxHealth(health);
                b.setHealth(health);
                break;
            case GHAST:
                Ghast g = player.getWorld().spawn(player.getLocation(), Ghast.class);
                g.setCustomName(name);
                g.setCustomNameVisible(true);
                g.setMaxHealth(health);
                g.setHealth(health);
                break;
            case ENDERMAN:
                Enderman e = player.getWorld().spawn(player.getLocation(), Enderman.class);
                e.setCustomName(name);
                e.setCustomNameVisible(true);
                e.setMaxHealth(health);
                e.setHealth(health);
                break;
            case POLAR_BEAR:
                PolarBear pb = player.getWorld().spawn(player.getLocation(), PolarBear.class);
                pb.setCustomName(name);
                pb.setCustomNameVisible(true);
                pb.setAge(age);
                pb.setMaxHealth(health);
                pb.setHealth(health);
                break;
            case VINDICATOR:
                Vindicator vi = player.getWorld().spawn(player.getLocation(), Vindicator.class);
                vi.setCustomName(name);
                vi.setCustomNameVisible(true);
                vi.setMaxHealth(health);
                vi.setHealth(health);
                break;
            case RABBIT:
                Rabbit r = player.getWorld().spawn(player.getLocation(), Rabbit.class);
                r.setCustomName(name);
                r.setCustomNameVisible(true);
                r.setAge(age);
                r.setMaxHealth(health);
                r.setHealth(health);
                r.setRabbitType(rabbitType);
                break;
            case SQUID:
                Squid sq = player.getWorld().spawn(player.getLocation(), Squid.class);
                sq.setCustomName(name);
                sq.setCustomNameVisible(true);
                sq.setMaxHealth(health);
                sq.setHealth(health);
                break;
            case PARROT:
                Parrot p = player.getWorld().spawn(player.getLocation(), Parrot.class);
                p.setCustomName(name);
                p.setCustomNameVisible(true);
                p.setMaxHealth(health);
                p.setHealth(health);
                p.setVariant(parrotType);
                break;
            case SHULKER:
                Shulker shu = player.getWorld().spawn(player.getLocation(), Shulker.class);
                shu.setCustomName(name);
                shu.setCustomNameVisible(true);
                shu.setMaxHealth(health);
                shu.setHealth(health);
                shu.setColor(shul);
                break;
        }
    }
}
