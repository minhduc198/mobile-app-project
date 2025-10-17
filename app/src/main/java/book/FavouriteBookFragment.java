package book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.openlibraryapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteBookFragment extends Fragment {

    private RecyclerView rcvFavourite;
    private FavouriteAdapter adapter;
    private List<Favourite> favouriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite_book, container, false);

        rcvFavourite = view.findViewById(R.id.recv_favour);
        adapter = new FavouriteAdapter(requireContext());

        favouriteList = new ArrayList<>();
        favouriteList.add(new Favourite(R.drawable.rin,getString(R.string.title1),
                "Emily Rodda", "0439385652",
                "Bravest heart will carry on when sleep is death, and hope is gone. Rowan doesn't believe he has a brave heart. But when the river that supports his village of Rin runs dry, he must join a dangerous journey to its source in the forbidden Mountain. To save Rin, Rowan and his companions must conquer not only the Mountain's many tricks, but also the fierce dragon that lives at its peak. ",
                140 , 10, 14));
        favouriteList.add(new Favourite(R.drawable.qveene,getString(R.string.title2),
                "Edmund Spenser","058209951X",
                "The Faerie Queene was one of the most influential poems in the English language. Dedicating his work to Elizabeth I, Spenser brilliantly united Arthurian romance and Italian renaissance epic to celebrate the glory of the Virgin Queen. Each book of the poem recounts the quest of a knight to achieve a virtue: the Red Crosse Knight of Holinesse, who must slay a dragon and free himself from the witch Duessa; Sir Guyon, Knight of Temperance, who escapes the Cave of Mammon and destroys Acrasia's Bowre of Bliss; and the lady-knight Britomart's search for her Sir Artegall, revealed to her in an enchanted mirror. Although composed as a moral and political allegory, The Faerie Queene's magical atmosphere captivated the imaginations of later poets from Milton to the Victorians.",
                120, 3, 4));
        favouriteList.add(new Favourite(R.drawable.shanna,getString(R.string.title3),
                "Kathleen E. Woodiwiss","0861883861",
                "Sensuous free spirit Shanna Trahern takes flight to a lush Caribbean paradise -- leaving the dashing condemned Ruark Beauchamp to the gallows of Newgate Prison. But no hangman's noose will deny Ruark the ecstasy of his true love. An exquisite beauty in desperate need . . . A condemned man with nothing left to lose . . . A magnificent tale of freedom and passionate destiny from the incomparable storyteller-- Kathleen E. Woodiwiss SHANNA\n" + "Behind the foreboding walls of Newgate Prison, a pact is sealed in secret--as a dashing and doomed criminal consents to wed a beautiful heiress . . . in return for one night of unparalleled pleasure. In the fading echoes of hollow wedding vows, a promise is broken--as a sensuous free-spirit flees to a lush Caribbean paradise, abandoning the handsome stranger she married to the gallows. But Ruark Beauchamp's destiny is now eternally intertwined with his exquisite, tempestuous Shanna's. And no iron ever forged can imprison his magnificent passion . . . and no hangman's noose will deny him the ecstasy that is rightfully his. ",
                154, 20 , 33));
        favouriteList.add(new Favourite(R.drawable.tower,getString(R.string.title4),
                "Tanith Lee", "0142300306",
                "All her life, Claidi has endured hardship in the House, where she must obey a spoiled princess. Then a golden stranger arrives, living proof of a world beyond the House walls. Claidi risks all to free the charming prisoner and accompanies him across the Waste toward his faraway home. It is a difficult yet marvelous journey, and all the while Claidi is at the side of a man she could come to love. That is, until they reach his home . . . and the Wolf Tower.",
                28, 1, 5));
        favouriteList.add(new Favourite(R.drawable.hobbit,getString(R.string.title5),"","","",0,0,0));
        favouriteList.add(new Favourite(R.drawable.ring,getString(R.string.title6),
                "J.R.R. Tolkien", "9780544003415",
                "Originally published from 1954 through 1956, J.R.R. Tolkien's richly complex series ushered in a new age of epic adventure storytelling. A philologist and illustrator who took inspiration from his work, Tolkien invented the modern heroic quest novel from the ground up, creating not just a world, but a domain, not just a lexicon, but a language, that would spawn countless imitators and lead to the inception of the epic fantasy genre. Today, THE LORD OF THE RINGS is considered \"the most influential fantasy novel ever written.\" (THE ENCYCLOPEDIA OF FANTASY)\n\nDuring his travels across Middle-earth, the hobbit Bilbo Baggins had found the Ring. But the simple band of gold was far from ordinary; it was in fact the One Ring - the greatest of the ancient Rings of Power. Sauron, the Dark Lord, had infused it with his own evil magic, and when it was lost, he was forced to flee into hiding.\n\nBut now Sauron's exile has ended and his power is spreading anew, fueled by the knowledge that his treasure has been found. He has gathered all the Great Rings to him, and will stop at nothing to reclaim the One that will complete his dominion. The only way to stop him is to cast the Ruling Ring deep into the Fire-Mountain at the heart of the land of Mordor--Sauron's dark realm.\n\nFate has placed the burden in the hands of Frodo Baggins, Bilbo's heir...and he is resolved to bear it to its end. Or his own.",
                1998, 158, 188));
        favouriteList.add(new Favourite(R.drawable.throne,getString(R.string.title7),"George R. R. Martin", "9780553103540",
                "A Game of Thrones is the inaugural novel in A Song of Ice and Fire, an epic series of fantasy novels crafted by the American author George R. R. Martin. Published on August 1, 1996, this novel introduces readers to the richly detailed world of Westeros and Essos, where political intrigue, power struggles, and magical elements intertwine.\n\nThe story unfolds through multiple perspectives, each chapter focusing on a different character, allowing readers to experience the narrative from various angles. This complex structure has become a hallmark of Martin's storytelling, immersing readers in the lives and motivations of a diverse cast.\n\nPlot Summary\n\nSet in the fictional continents of Westeros and Essos, the narrative revolves around the power struggles among noble families vying for the Iron Throne, the seat of power in the Seven Kingdoms of Westeros. The story is rich with political intrigue, betrayal, and epic battles, as well as a deep exploration of themes such as loyalty, honor, and the consequences of power.\n\nThemes\n\nThe novel explores themes of power, loyalty, and the moral complexities of leadership. It delves into the consequences of ambition and the struggle between personal honor and political necessity. The richly detailed world-building and intricate character development make A Game of Thrones a compelling and immersive read.\n\nKey Characters\n\n• Eddard \"Ned\" Stark: The honorable Lord of Winterfell and Warden of the North\n• Catelyn Stark: The devoted wife of Eddard Stark\n• Robert Baratheon: The King of the Seven Kingdoms\n• Cersei Lannister: The ambitious and cunning Queen of Westeros\n• Jaime Lannister: A skilled swordsman and member of the Kingsguard\n• Tyrion Lannister: The witty and resourceful dwarf\n• Daenerys Targaryen: An exiled princess of House Targaryen\n• Jon Snow: The bastard son of Eddard Stark\n• Sansa Stark: The eldest daughter of Eddard Stark\n• Arya Stark: The youngest daughter of Eddard Stark\n• Bran Stark: The second son of Eddard Stark",
                10591, 808, 832));


        adapter.setData(favouriteList);

        rcvFavourite.setLayoutManager(new GridLayoutManager(requireContext(),2));
        rcvFavourite.setAdapter(adapter);

        return view;
    }
}