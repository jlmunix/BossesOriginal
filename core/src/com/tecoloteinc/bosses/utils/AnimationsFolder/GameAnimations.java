package com.tecoloteinc.bosses.utils.AnimationsFolder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameAnimations {
    private GameAnimations() {
    }

    public static AnimationsEngine.AnimationSequenceType[] rationAnimation() {
        AnimationsEngine animationsEngineHandler;
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[4];

        // Frame 1
        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = .05f;
        animation_sequence[0].horizontal_frame = 0;
        animation_sequence[0].vertical_frame = 0;
        animation_sequence[0].vertical_frame = 0;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;
        // Frame 2
        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = .05f;
        animation_sequence[1].horizontal_frame = 0;
        animation_sequence[1].vertical_frame = 2;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;
        // Frame 3
        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = .05f;
        animation_sequence[2].horizontal_frame = 0;
        animation_sequence[2].vertical_frame = 1;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;
        // Frame 4
        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = .05f;
        animation_sequence[3].horizontal_frame = 0;
        animation_sequence[3].vertical_frame = 3;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;
        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] attack() {
        AnimationsEngine animationsEngineHandler;
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[9];

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/sword.wav"));
        sound.setVolume(0, 0.5f);
        sound.play();

        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = 0.02f;
        animation_sequence[0].horizontal_frame = 0;
        animation_sequence[0].vertical_frame = -1;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;

        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = 0.02f;
        animation_sequence[1].horizontal_frame = 1;
        animation_sequence[1].vertical_frame = -1;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;

        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = 0.02f;
        animation_sequence[2].horizontal_frame = 2;
        animation_sequence[2].vertical_frame = -1;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;

        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = 0.02f;
        animation_sequence[3].horizontal_frame = 3;
        animation_sequence[3].vertical_frame = -1;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;

        animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[4].duration_time = 0.02f;
        animation_sequence[4].horizontal_frame = 4;
        animation_sequence[4].vertical_frame = -1;
        animation_sequence[4].pos_x = 0;
        animation_sequence[4].pos_y = 0;

        animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[5].duration_time = 0.02f;
        animation_sequence[5].horizontal_frame = 5;
        animation_sequence[5].vertical_frame = -1;
        animation_sequence[5].pos_x = 0;
        animation_sequence[5].pos_y = 0;

        animation_sequence[6] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[6].duration_time = 0.02f;
        animation_sequence[6].horizontal_frame = 6;
        animation_sequence[6].vertical_frame = -1;
        animation_sequence[6].pos_x = 0;
        animation_sequence[6].pos_y = 0;

        animation_sequence[7] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[7].duration_time = 0.02f;
        animation_sequence[7].horizontal_frame = 7;
        animation_sequence[7].vertical_frame = -1;
        animation_sequence[7].pos_x = 0;
        animation_sequence[7].pos_y = 0;

        animation_sequence[8] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[8].duration_time = 0.02f;
        animation_sequence[8].horizontal_frame = 8;
        animation_sequence[8].vertical_frame = -1;
        animation_sequence[8].pos_x = 0;
        animation_sequence[8].pos_y = 0;

        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] die() {
        AnimationsEngine animationsEngineHandler;
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[15];

        Sound sound = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/SwordCharging.wav"));
        sound.setVolume(0, 0.5f);
        sound.play();
        // Frame 1
        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = .07f;
        animation_sequence[0].horizontal_frame = 0;
        animation_sequence[0].vertical_frame = 0;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;
        // Frame 2
        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = .07f;
        animation_sequence[1].horizontal_frame = 0;
        animation_sequence[1].vertical_frame = 2;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;
        // Frame 3
        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = .07f;
        animation_sequence[2].horizontal_frame = 0;
        animation_sequence[2].vertical_frame = 1;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;
        // Frame 4
        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = .07f;
        animation_sequence[3].horizontal_frame = 0;
        animation_sequence[3].vertical_frame = 3;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;
        // Frame 1
        animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[4].duration_time = .07f;
        animation_sequence[4].horizontal_frame = 0;
        animation_sequence[4].vertical_frame = 0;
        animation_sequence[4].pos_x = 0;
        animation_sequence[4].pos_y = 0;
        // Frame 2
        animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[5].duration_time = .07f;
        animation_sequence[5].horizontal_frame = 0;
        animation_sequence[5].vertical_frame = 2;
        animation_sequence[5].pos_x = 0;
        animation_sequence[5].pos_y = 0;
        // Frame 3
        animation_sequence[6] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[6].duration_time = .07f;
        animation_sequence[6].horizontal_frame = 0;
        animation_sequence[6].vertical_frame = 1;
        animation_sequence[6].pos_x = 0;
        animation_sequence[6].pos_y = 0;
        // Frame 4
        animation_sequence[7] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[7].duration_time = .07f;
        animation_sequence[7].horizontal_frame = 0;
        animation_sequence[7].vertical_frame = 3;
        animation_sequence[7].pos_x = 0;
        animation_sequence[7].pos_y = 0;
        sound.stop();
        sound = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/casse.wav"));
        sound.setVolume(0, 0.5f);
        sound.play();
        // Frame 1
        animation_sequence[8] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[8].duration_time = .2f;
        animation_sequence[8].horizontal_frame = 0;
        animation_sequence[8].vertical_frame = 8;
        animation_sequence[8].pos_x = 0;
        animation_sequence[8].pos_y = 0;
        // Frame 2
        animation_sequence[9] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[9].duration_time = .2f;
        animation_sequence[9].horizontal_frame = 1;
        animation_sequence[9].vertical_frame = 8;
        animation_sequence[9].pos_x = 0;
        animation_sequence[9].pos_y = 0;
        // Frame 3
        animation_sequence[10] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[10].duration_time = .2f;
        animation_sequence[10].horizontal_frame = 2;
        animation_sequence[10].vertical_frame = 8;
        animation_sequence[10].pos_x = 0;
        animation_sequence[10].pos_y = 0;
        // Frame 5
        animation_sequence[11] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[11].duration_time = 1f;
        animation_sequence[11].horizontal_frame = 5;
        animation_sequence[11].vertical_frame = 8;
        animation_sequence[11].pos_x = 0;
        animation_sequence[11].pos_y = 0;
        // Frame 4
        animation_sequence[12] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[12].duration_time = .05f;
        animation_sequence[12].horizontal_frame = 3;
        animation_sequence[12].vertical_frame = 8;
        animation_sequence[12].pos_x = 0;
        animation_sequence[12].pos_y = 0;

        // Frame 5
        animation_sequence[13] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[13].duration_time = .05f;
        animation_sequence[13].horizontal_frame = 4;
        animation_sequence[13].vertical_frame = 8;
        animation_sequence[13].pos_x = 0;
        animation_sequence[13].pos_y = 0;
        // Frame 5
        animation_sequence[14] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[14].duration_time = .2f;
        animation_sequence[14].horizontal_frame = 5;
        animation_sequence[14].vertical_frame = 8;
        animation_sequence[14].pos_x = 0;
        animation_sequence[14].pos_y = 0;
        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] swordAttack(int currentSide) {
        AnimationsEngine animationsEngineHandler;
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[9];
        if (currentSide == 0) {

            animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[0].duration_time = 0.02f;
            animation_sequence[0].horizontal_frame = 0;
            animation_sequence[0].vertical_frame = -1;
            animation_sequence[0].pos_x = 8;
            animation_sequence[0].pos_y = 8;

            animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[1].duration_time = 0.02f;
            animation_sequence[1].horizontal_frame = 1;
            animation_sequence[1].vertical_frame = -1;
            animation_sequence[1].pos_x = 15;
            animation_sequence[1].pos_y = 7;

            animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[2].duration_time = 0.02f;
            animation_sequence[2].horizontal_frame = 2;
            animation_sequence[2].vertical_frame = -1;
            animation_sequence[2].pos_x = 15;
            animation_sequence[2].pos_y = 7;

            animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[3].duration_time = 0.02f;
            animation_sequence[3].horizontal_frame = 3;
            animation_sequence[3].vertical_frame = -1;
            animation_sequence[3].pos_x = 14;
            animation_sequence[3].pos_y = 3;

            animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[4].duration_time = 0.02f;
            animation_sequence[4].horizontal_frame = 4;
            animation_sequence[4].vertical_frame = -1;
            animation_sequence[4].pos_x = 10;
            animation_sequence[4].pos_y = 1;

            animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[5].duration_time = 0.02f;
            animation_sequence[5].horizontal_frame = 5;
            animation_sequence[5].vertical_frame = -1;
            animation_sequence[5].pos_x = 14;
            animation_sequence[5].pos_y = -2;

            animation_sequence[6] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[6].duration_time = 0.02f;
            animation_sequence[6].horizontal_frame = 6;
            animation_sequence[6].vertical_frame = -1;
            animation_sequence[6].pos_x = 7;
            animation_sequence[6].pos_y = -7;

            animation_sequence[7] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[7].duration_time = 0.02f;
            animation_sequence[7].horizontal_frame = 7;
            animation_sequence[7].vertical_frame = -1;
            animation_sequence[7].pos_x = 1;
            animation_sequence[7].pos_y = -6;

            animation_sequence[8] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[8].duration_time = 0.02f;
            animation_sequence[8].horizontal_frame = 8;
            animation_sequence[8].vertical_frame = -1;
            animation_sequence[8].pos_x = 1;
            animation_sequence[8].pos_y = -6;
        }
        if (1 == currentSide) {
            animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[0].duration_time = 0.02f;
            animation_sequence[0].horizontal_frame = 0;
            animation_sequence[0].vertical_frame = -1;
            animation_sequence[0].pos_x = -8;
            animation_sequence[0].pos_y = 8;

            animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[1].duration_time = 0.02f;
            animation_sequence[1].horizontal_frame = 1;
            animation_sequence[1].vertical_frame = -1;
            animation_sequence[1].pos_x = -15;
            animation_sequence[1].pos_y = 7;

            animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[2].duration_time = 0.02f;
            animation_sequence[2].horizontal_frame = 2;
            animation_sequence[2].vertical_frame = -1;
            animation_sequence[2].pos_x = -15;
            animation_sequence[2].pos_y = 7;

            animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[3].duration_time = 0.02f;
            animation_sequence[3].horizontal_frame = 3;
            animation_sequence[3].vertical_frame = -1;
            animation_sequence[3].pos_x = -14;
            animation_sequence[3].pos_y = 3;

            animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[4].duration_time = 0.02f;
            animation_sequence[4].horizontal_frame = 4;
            animation_sequence[4].vertical_frame = -1;
            animation_sequence[4].pos_x = -10;
            animation_sequence[4].pos_y = 1;

            animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[5].duration_time = 0.02f;
            animation_sequence[5].horizontal_frame = 5;
            animation_sequence[5].vertical_frame = -1;
            animation_sequence[5].pos_x = -14;
            animation_sequence[5].pos_y = -2;

            animation_sequence[6] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[6].duration_time = 0.02f;
            animation_sequence[6].horizontal_frame = 6;
            animation_sequence[6].vertical_frame = -1;
            animation_sequence[6].pos_x = -7;
            animation_sequence[6].pos_y = -7;

            animation_sequence[7] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[7].duration_time = 0.02f;
            animation_sequence[7].horizontal_frame = 7;
            animation_sequence[7].vertical_frame = -1;
            animation_sequence[7].pos_x = -1;
            animation_sequence[7].pos_y = -6;

            animation_sequence[8] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[8].duration_time = 0.02f;
            animation_sequence[8].horizontal_frame = 8;
            animation_sequence[8].vertical_frame = -1;
            animation_sequence[8].pos_x = -1;
            animation_sequence[8].pos_y = -6;
        }

        if (2 == currentSide) {
            animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[0].duration_time = 0.02f;
            animation_sequence[0].horizontal_frame = 0;
            animation_sequence[0].vertical_frame = -1;
            animation_sequence[0].pos_x = 7;
            animation_sequence[0].pos_y = 3;

            animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[1].duration_time = 0.02f;
            animation_sequence[1].horizontal_frame = 1;
            animation_sequence[1].vertical_frame = -1;
            animation_sequence[1].pos_x = 3;
            animation_sequence[1].pos_y = 8;

            animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[2].duration_time = 0.02f;
            animation_sequence[2].horizontal_frame = 2;
            animation_sequence[2].vertical_frame = -1;
            animation_sequence[2].pos_x = 4;
            animation_sequence[2].pos_y = 10;

            animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[3].duration_time = 0.02f;
            animation_sequence[3].horizontal_frame = 3;
            animation_sequence[3].vertical_frame = -1;
            animation_sequence[3].pos_x = 4;
            animation_sequence[3].pos_y = 12;

            animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[4].duration_time = 0.02f;
            animation_sequence[4].horizontal_frame = 4;
            animation_sequence[4].vertical_frame = -1;
            animation_sequence[4].pos_x = 0;
            animation_sequence[4].pos_y = 14;

            animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[5].duration_time = 0.02f;
            animation_sequence[5].horizontal_frame = 5;
            animation_sequence[5].vertical_frame = -1;
            animation_sequence[5].pos_x = -1;
            animation_sequence[5].pos_y = 11;

            animation_sequence[6] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[6].duration_time = 0.02f;
            animation_sequence[6].horizontal_frame = 6;
            animation_sequence[6].vertical_frame = -1;
            animation_sequence[6].pos_x = -5;
            animation_sequence[6].pos_y = 6;

            animation_sequence[7] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[7].duration_time = 0.02f;
            animation_sequence[7].horizontal_frame = 7;
            animation_sequence[7].vertical_frame = -1;
            animation_sequence[7].pos_x = -10;
            animation_sequence[7].pos_y = 8;

            animation_sequence[8] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[8].duration_time = 0.02f;
            animation_sequence[8].horizontal_frame = 8;
            animation_sequence[8].vertical_frame = -1;
            animation_sequence[8].pos_x = -9;
            animation_sequence[8].pos_y = 5;

        }

        if (3 == currentSide) {
            animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[0].duration_time = 0.02f;
            animation_sequence[0].horizontal_frame = 0;
            animation_sequence[0].vertical_frame = -1;
            animation_sequence[0].pos_x = -10;
            animation_sequence[0].pos_y = 1;

            animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[1].duration_time = 0.02f;
            animation_sequence[1].horizontal_frame = 1;
            animation_sequence[1].vertical_frame = -1;
            animation_sequence[1].pos_x = -6;
            animation_sequence[1].pos_y = -1;

            animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[2].duration_time = 0.02f;
            animation_sequence[2].horizontal_frame = 2;
            animation_sequence[2].vertical_frame = -1;
            animation_sequence[2].pos_x = -1;
            animation_sequence[2].pos_y = -4;

            animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[3].duration_time = 0.02f;
            animation_sequence[3].horizontal_frame = 3;
            animation_sequence[3].vertical_frame = -1;
            animation_sequence[3].pos_x = 0;
            animation_sequence[3].pos_y = -5;

            animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[4].duration_time = 0.02f;
            animation_sequence[4].horizontal_frame = 4;
            animation_sequence[4].vertical_frame = -1;
            animation_sequence[4].pos_x = 4;
            animation_sequence[4].pos_y = -7;

            animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[5].duration_time = 0.02f;
            animation_sequence[5].horizontal_frame = 5;
            animation_sequence[5].vertical_frame = -1;
            animation_sequence[5].pos_x = 7;
            animation_sequence[5].pos_y = -5;

            animation_sequence[6] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[6].duration_time = 0.02f;
            animation_sequence[6].horizontal_frame = 6;
            animation_sequence[6].vertical_frame = -1;
            animation_sequence[6].pos_x = 8;
            animation_sequence[6].pos_y = -6;

            animation_sequence[7] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[7].duration_time = 0.02f;
            animation_sequence[7].horizontal_frame = 7;
            animation_sequence[7].vertical_frame = -1;
            animation_sequence[7].pos_x = 13;
            animation_sequence[7].pos_y = -4;

            animation_sequence[8] = new AnimationsEngine.AnimationSequenceType();
            animation_sequence[8].duration_time = 0.02f;
            animation_sequence[8].horizontal_frame = 8;
            animation_sequence[8].vertical_frame = -1;
            animation_sequence[8].pos_x = 13;
            animation_sequence[8].pos_y = -4;
        }


        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] EyeBossPresentation() {
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[5];

        // Frame 0
        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = 0.2f;
        animation_sequence[0].horizontal_frame = 0;
        animation_sequence[0].vertical_frame = 4;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;
        // Frame 1
        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = 0.2f;
        animation_sequence[1].horizontal_frame = 0;
        animation_sequence[1].vertical_frame = 4;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;
        // Frame 2
        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = .2f;
        animation_sequence[2].horizontal_frame = 1;
        animation_sequence[2].vertical_frame = 4;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;
        // Frame 3
        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = .2f;
        animation_sequence[3].horizontal_frame = 0;
        animation_sequence[3].vertical_frame = 5;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;
        // Frame 4
        animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[4].duration_time = .2f;
        animation_sequence[4].horizontal_frame = 1;
        animation_sequence[4].vertical_frame = 5;
        animation_sequence[4].pos_x = 0;
        animation_sequence[4].pos_y = 0;
        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] EyeBossHide() {
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[6];

        // Frame 0
        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = 0.3f;
        animation_sequence[0].horizontal_frame = 0;
        animation_sequence[0].vertical_frame = 6;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;
        // Frame 1
        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = 0.3f;
        animation_sequence[1].horizontal_frame = 1;
        animation_sequence[1].vertical_frame = 6;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;
        // Frame 2
        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = .3f;
        animation_sequence[2].horizontal_frame = 0;
        animation_sequence[2].vertical_frame = 7;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;
        // Frame 3
        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = .3f;
        animation_sequence[3].horizontal_frame = 1;
        animation_sequence[3].vertical_frame = 7;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;
        // Frame 4
        animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[4].duration_time = .3f;
        animation_sequence[4].horizontal_frame = 0;
        animation_sequence[4].vertical_frame = 8;
        animation_sequence[4].pos_x = 0;
        animation_sequence[4].pos_y = 0;
        // Frame 4
        animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[5].duration_time = .3f;
        animation_sequence[5].horizontal_frame = 1;
        animation_sequence[5].vertical_frame = 8;
        animation_sequence[5].pos_x = 0;
        animation_sequence[5].pos_y = 0;
        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] EyeBossSpawn() {
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[6];

        // Frame 0
        animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[5].duration_time = 0.1f;
        animation_sequence[5].horizontal_frame = 0;
        animation_sequence[5].vertical_frame = 6;
        animation_sequence[5].pos_x = 0;
        animation_sequence[5].pos_y = 0;
        // Frame 1
        animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[4].duration_time = 0.1f;
        animation_sequence[4].horizontal_frame = 1;
        animation_sequence[4].vertical_frame = 6;
        animation_sequence[4].pos_x = 0;
        animation_sequence[4].pos_y = 0;
        // Frame 24
        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = .1f;
        animation_sequence[3].horizontal_frame = 0;
        animation_sequence[3].vertical_frame = 7;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;
        // Frame 33
        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = .1f;
        animation_sequence[2].horizontal_frame = 1;
        animation_sequence[2].vertical_frame = 7;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;
        // Frame 4
        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = .1f;
        animation_sequence[1].horizontal_frame = 0;
        animation_sequence[1].vertical_frame = 8;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;
        // Frame 4
        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = .1f;
        animation_sequence[0].horizontal_frame = 1;
        animation_sequence[0].vertical_frame = 8;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;
        return animation_sequence;
    }

    public static AnimationsEngine.AnimationSequenceType[] Explosion() {
        AnimationsEngine.AnimationSequenceType[] animation_sequence = new AnimationsEngine.AnimationSequenceType[6];

        // Frame 0
        animation_sequence[0] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[0].duration_time = 0.1f;
        animation_sequence[0].horizontal_frame = 0;
        animation_sequence[0].vertical_frame = 0;
        animation_sequence[0].pos_x = 0;
        animation_sequence[0].pos_y = 0;
        // Frame 1
        animation_sequence[1] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[1].duration_time = 0.1f;
        animation_sequence[1].horizontal_frame = 1;
        animation_sequence[1].vertical_frame = 0;
        animation_sequence[1].pos_x = 0;
        animation_sequence[1].pos_y = 0;
        // Frame 24
        animation_sequence[2] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[2].duration_time = .1f;
        animation_sequence[2].horizontal_frame = 2;
        animation_sequence[2].vertical_frame = 0;
        animation_sequence[2].pos_x = 0;
        animation_sequence[2].pos_y = 0;
        // Frame 33
        animation_sequence[3] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[3].duration_time = .1f;
        animation_sequence[3].horizontal_frame = 3;
        animation_sequence[3].vertical_frame = 0;
        animation_sequence[3].pos_x = 0;
        animation_sequence[3].pos_y = 0;
        // Frame 4
        animation_sequence[4] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[4].duration_time = .1f;
        animation_sequence[4].horizontal_frame = 4;
        animation_sequence[4].vertical_frame = 0;
        animation_sequence[4].pos_x = 0;
        animation_sequence[4].pos_y = 0;
        // Frame 4
        animation_sequence[5] = new AnimationsEngine.AnimationSequenceType();
        animation_sequence[5].duration_time = .1f;
        animation_sequence[5].horizontal_frame = 5;
        animation_sequence[5].vertical_frame = 0;
        animation_sequence[5].pos_x = 0;
        animation_sequence[5].pos_y = 0;
        return animation_sequence;
    }
}