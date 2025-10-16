package com.mobile.openlibraryapp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import book.Book1;
import book.BookAdapter1;
import category.Category;
import category.CategoryAdapter;
import com.mobile.openlibraryapp.WelcomeAdapter;
import com.mobile.openlibraryapp.Welcome;


import ApiSearch.Book;
import ApiSearch.BookAdapter;
import ApiSearch.BookSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;
import ApiSearch.ApiClient;
import ApiSearch.OpenLibraryApiService;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText searchEditText;
    private RecyclerView booksRecyclerView;
    private TextView emptyTextView;
    private BookAdapter bookAdapter;
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private retrofit2.Call<BookSearchResponse> currentCall;
    private static final int SEARCH_TRIGGER_DELAY_IN_MS = 500;
    private static final int MIN_QUERY_LENGTH = 3;
    private View search;
    private ProgressBar progressbar;

    private RecyclerView recvCategory;
    private CategoryAdapter categoryAdapter;
    private DrawerLayout drawerLayout;

    private RecyclerView recvWelcome;
    private WelcomeAdapter welcomeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDrawerListener();

        // Khởi tạo Welcome RecyclerView
        recvWelcome = findViewById(R.id.recv_welcome);
        welcomeAdapter = new WelcomeAdapter(this);

        LinearLayoutManager welcomeLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recvWelcome.setLayoutManager(welcomeLayoutManager);
        List<Welcome> welcomeList = getWelcomeData();
        welcomeAdapter.setData(welcomeList);
        recvWelcome.setAdapter(welcomeAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recvWelcome);

        // Khởi tạo Category
        recvCategory = findViewById(R.id.recv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        recvCategory.setAdapter(categoryAdapter);

        // Khởi tạo Drawer và RecyclerView cho Book
        drawerLayout = findViewById(R.id.drawerLayout);

        // Initialize views
        search = findViewById(R.id.search_result);
        View head = findViewById(R.id.headerFragment);
        searchEditText = head.findViewById(R.id.searchBox);
        booksRecyclerView = search.findViewById(R.id.booksRecyclerView);
        emptyTextView = search.findViewById(R.id.emptyTextView);
        progressbar = search.findViewById(R.id.progressBar);

        // Setup RecyclerView
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this, new ArrayList<>());
        booksRecyclerView.setAdapter(bookAdapter);

        //Connect fragment BookDetail in MainActivity
        BookAdapter1 adapter = new BookAdapter1(MainActivity.this);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Remove any pending searches
                searchHandler.removeCallbacks(searchRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();

                searchRunnable = () -> performSearch(query);

                if (query.length() >= MIN_QUERY_LENGTH) {
                    // Schedule the search to run after the delay
                    searchHandler.postDelayed(searchRunnable, SEARCH_TRIGGER_DELAY_IN_MS);
                } else {
                    // If query is too short, clear the results
                    bookAdapter.updateBooks(new ArrayList<>());
                    booksRecyclerView.setVisibility(GONE);
                    emptyTextView.setVisibility(GONE);
                    search.setVisibility(GONE);
                }
            }
        });
    }

    private void performSearch(String query) {
        if (currentCall != null && currentCall.isExecuted() && !currentCall.isCanceled()) {
            currentCall.cancel();
        }

        booksRecyclerView.setVisibility(GONE);
        emptyTextView.setVisibility(GONE);
        search.setVisibility(VISIBLE);
        progressbar.setVisibility(View.VISIBLE);

        OpenLibraryApiService apiService = ApiClient.getApiService();

        currentCall = apiService.searchBooks(query);

        currentCall.enqueue(new Callback<BookSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookSearchResponse> call, @NonNull Response<BookSearchResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().getDocs().isEmpty()) {

                    booksRecyclerView.setVisibility(VISIBLE);
                    bookAdapter.updateBooks(response.body().getDocs());
                } else if (response.isSuccessful()) {
                    emptyTextView.setText("No books found for \"" + query + "\"");
                    emptyTextView.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookSearchResponse> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                emptyTextView.setText("Failed to load data. Check your connection.");
                emptyTextView.setVisibility(VISIBLE);
                progressbar.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupDrawerListener(){
        // Khởi tạo các TextView trong menu
        TextView itemSubjects = findViewById(R.id.itemSubjects);
        TextView itemTrending = findViewById(R.id.itemTrending);
        TextView itemLibraryExplorer = findViewById(R.id.itemLibraryExplorer);
        TextView itemLists = findViewById(R.id.itemLists);
        TextView itemCollections = findViewById(R.id.itemCollections);
        TextView itemK12 = findViewById(R.id.itemK12);
        TextView itemBookTalks = findViewById(R.id.itemBookTalks);
        TextView itemRandomBook = findViewById(R.id.itemRandomBook);
        TextView itemAdvancedSearch = findViewById(R.id.itemAdvancedSearch);
        TextView itemAddBook = findViewById(R.id.itemAddBook);
        TextView itemRecentEdits = findViewById(R.id.itemRecentEdits);
        TextView itemHelp = findViewById(R.id.itemHelp);
        TextView itemDevCenter = findViewById(R.id.itemDevCenter);

        // Setup click cho các TextView
        itemSubjects.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/subjects");
        });
        itemTrending.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/trending/now");
        });
        itemLibraryExplorer.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/explore");
        });
        itemLists.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/lists");
        });
        itemCollections.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/collections");
        });
        itemK12.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/collections/k-12");
        });
        itemBookTalks.setOnClickListener(v -> {
            openWebPage("https://archive.org/details/booktalks");
        });
        itemRandomBook.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/random-book");
        });
        itemAdvancedSearch.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/advancedsearch");
        });
        itemAddBook.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/add-a-book");
        });
        itemRecentEdits.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/recent-community-edits");
        });
        itemHelp.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/help-support");
        });
        itemDevCenter.setOnClickListener(v -> {
            openWebPage("https://openlibrary.org/developer-center");
        });
    }

    private List<Welcome> getWelcomeData() {
        List<Welcome> welcomeList = new ArrayList<>();

        welcomeList.add(new Welcome(R.drawable.welcome1, "Read Free Library Books Online", "Millions of books available through Controlled Digital Lending"));
        welcomeList.add(new Welcome(R.drawable.welcome2,"Set a Yearly Reading Goal", "Learn how to set a yearly reading goal and track what you read"));
        welcomeList.add(new Welcome(R.drawable.welcome3,"Keep Track of your Favorite Books", "Organize your Books using Lists & the Reading Log"));
        welcomeList.add(new Welcome(R.drawable.welcome4,"Try the virtual Library Explorer", "Digital shelves organized like a physical library"));
        welcomeList.add(new Welcome(R.drawable.welcome5,"Try Fulltext Search", "Find matching results within the text of millions of books"));
        welcomeList.add(new Welcome(R.drawable.welcome6,"Be an Open Librarian", "Dozens of ways you can help improve the library"));
        welcomeList.add(new Welcome(R.drawable.welcome7,"Volunteer at Open Library", "Discover opportunities to improve the library"));
        welcomeList.add(new Welcome(R.drawable.welcome8,"Send us feedback", "Your feedback will help us improve these cards"));
        return welcomeList;
    }

    private List<Category> getListCategory() {
        List<Category> listCategory = new ArrayList<>();

        List<Book1> trendingBooks = new ArrayList<>();
        trendingBooks.add(new Book1("Shanna", "Kathleen E. Woodiwiss",R.drawable.book, "Read", "0861883861",
                "Sensuous free spirit Shanna Trahern takes flight to a lush Caribbean paradise -- leaving the dashing condemned Ruark Beauchamp to the gallows of Newgate Prison. But no hangman's noose will deny Ruark the ecstasy of his true love. An exquisite beauty in desperate need . . . A condemned man with nothing left to lose . . . A magnificent tale of freedom and passionate destiny from the incomparable storyteller-- Kathleen E. Woodiwiss SHANNA\n" + "Behind the foreboding walls of Newgate Prison, a pact is sealed in secret--as a dashing and doomed criminal consents to wed a beautiful heiress . . . in return for one night of unparalleled pleasure. In the fading echoes of hollow wedding vows, a promise is broken--as a sensuous free-spirit flees to a lush Caribbean paradise, abandoning the handsome stranger she married to the gallows. But Ruark Beauchamp's destiny is now eternally intertwined with his exquisite, tempestuous Shanna's. And no iron ever forged can imprison his magnificent passion . . . and no hangman's noose will deny him the ecstasy that is rightfully his. ",
                154, 20 , 33 ));
        trendingBooks.add(new Book1("Rowan of Rin", "Emily Rodda", R.drawable.book, "Read", "0439385652",
                "Bravest heart will carry on when sleep is death, and hope is gone. Rowan doesn't believe he has a brave heart. But when the river that supports his village of Rin runs dry, he must join a dangerous journey to its source in the forbidden Mountain. To save Rin, Rowan and his companions must conquer not only the Mountain's many tricks, but also the fierce dragon that lives at its peak. ",
                140 , 10, 14));
        trendingBooks.add(new Book1("Amber Brown Goes Fourth", "Paula Danziger", R.drawable.book, "Read", "0590934252",
                "Entering fourth grade, Amber faces some changes in her life as her best friend moves away and her parents divorce.",
                47, 4, 8));
        trendingBooks.add(new Book1("Night", "Elie Wiesel", R.drawable.book, "Read", "0553272535",
                "\"Night--A terrifying account of the Nazi death camp horror that turns a young Jewish boy into an agonized witness to the death of his family...the death of his innocence...and the death of his God. Penetrating and powerful, as personal as The diary of Anne Frank, Night awakens the shocking memory of evil at its absolute and carries with it the unforgettable message that this horror must never be allowed to happen again\"--Provided by publisher.",
                508, 33, 87));
        trendingBooks.add(new Book1("The Notorious Marquess", "Marlene Suson", R.drawable.book, "Read", "0449212165",
                "\"NO WOMAN'S REPUTATION CAN SURVIVE A JOURNEY UNDER THE NOTORIOUS MARQUESS'S PROTECTION!\"\n" + "\nThe scheme was madness! What scandal would erupt if it became public that Lady Annabelle Smythe" + " had traveled to Paris with that infamous libertine, Lord Ellerton.\n\nBut while posing as Miss Anna Smith, " + "governess to Ellerton's headstrong sister, Annabelle hoped to locate her cousin, Jean-Louis. For he was her last, " + "desperate hope to fight her greedy half brother's horrid schemes.\n\nNo one was more surprised than the " + "Notorious Marquess when he found himself charmed by the calm, capable, quick-witted Miss Smith. Despite his " + "cavalier reputation, it was not her virtue he sought ... but her heart!",
                16, 3, 9));

        List<Book1> classicBooks = new ArrayList<>();
        classicBooks.add(new Book1("Black Beauty", "Anna Sewell", R.drawable.book, "Read", "9781453054765",
                "A horse in nineteenth-century England recounts his experiences with both good and bad masters.",
                348, 30, 53));

        classicBooks.add(new Book1("The twins at St. Clare's", "Enid Blyton", R.drawable.book, "Read", "1405219777",
                "Pat(ricia) and Isabel O'Sullivan are identical twins - they want to go to a really expensive school but their parents decide to send them to St Clare's instead. So the two decide to do as worse as possible.",
                64, 3, 15));

        classicBooks.add(new Book1("The Faerie Qveene", "Edmund Spenser", R.drawable.book, "Read", "058209951X",
                "The Faerie Queene was one of the most influential poems in the English language. Dedicating his work to Elizabeth I, Spenser brilliantly united Arthurian romance and Italian renaissance epic to celebrate the glory of the Virgin Queen. Each book of the poem recounts the quest of a knight to achieve a virtue: the Red Crosse Knight of Holinesse, who must slay a dragon and free himself from the witch Duessa; Sir Guyon, Knight of Temperance, who escapes the Cave of Mammon and destroys Acrasia's Bowre of Bliss; and the lady-knight Britomart's search for her Sir Artegall, revealed to her in an enchanted mirror. Although composed as a moral and political allegory, The Faerie Queene's magical atmosphere captivated the imaginations of later poets from Milton to the Victorians.",
                120, 3, 4));

        classicBooks.add(new Book1("The Red and the Black", "Stendhal", R.drawable.book, "Read", "1840221275",
                "The story of an ambitious youth without birth or fortune in France in the 19th century.",
                113, 11, 23));

        classicBooks.add(new Book1("The History of Herodotus", "Herodotus", R.drawable.book, "Read", "140430732X",
                "One of the earliest histories of the western world still extant, this gives a contemporary account of the Greco-Persian wars of the fifth century BCE with the rise of the Achaemenid Empire under Cyrus the Great.",
                343, 28, 29));


        List<Book1> fantasyBooks = new ArrayList<>();
        fantasyBooks.add(new Book1("The Lord Of The Rings", "J.R.R. Tolkien", R.drawable.book, "Read", "9780544003415",
                "Originally published from 1954 through 1956, J.R.R. Tolkien's richly complex series ushered in a new age of epic adventure storytelling. A philologist and illustrator who took inspiration from his work, Tolkien invented the modern heroic quest novel from the ground up, creating not just a world, but a domain, not just a lexicon, but a language, that would spawn countless imitators and lead to the inception of the epic fantasy genre. Today, THE LORD OF THE RINGS is considered \"the most influential fantasy novel ever written.\" (THE ENCYCLOPEDIA OF FANTASY)\n\nDuring his travels across Middle-earth, the hobbit Bilbo Baggins had found the Ring. But the simple band of gold was far from ordinary; it was in fact the One Ring - the greatest of the ancient Rings of Power. Sauron, the Dark Lord, had infused it with his own evil magic, and when it was lost, he was forced to flee into hiding.\n\nBut now Sauron's exile has ended and his power is spreading anew, fueled by the knowledge that his treasure has been found. He has gathered all the Great Rings to him, and will stop at nothing to reclaim the One that will complete his dominion. The only way to stop him is to cast the Ruling Ring deep into the Fire-Mountain at the heart of the land of Mordor--Sauron's dark realm.\n\nFate has placed the burden in the hands of Frodo Baggins, Bilbo's heir...and he is resolved to bear it to its end. Or his own.",
                1998, 158, 188));

        fantasyBooks.add(new Book1("A Game of Thrones", "George R. R. Martin", R.drawable.book, "Read", "9780553103540",
                "A Game of Thrones is the inaugural novel in A Song of Ice and Fire, an epic series of fantasy novels crafted by the American author George R. R. Martin. Published on August 1, 1996, this novel introduces readers to the richly detailed world of Westeros and Essos, where political intrigue, power struggles, and magical elements intertwine.\n\nThe story unfolds through multiple perspectives, each chapter focusing on a different character, allowing readers to experience the narrative from various angles. This complex structure has become a hallmark of Martin's storytelling, immersing readers in the lives and motivations of a diverse cast.\n\nPlot Summary\n\nSet in the fictional continents of Westeros and Essos, the narrative revolves around the power struggles among noble families vying for the Iron Throne, the seat of power in the Seven Kingdoms of Westeros. The story is rich with political intrigue, betrayal, and epic battles, as well as a deep exploration of themes such as loyalty, honor, and the consequences of power.\n\nThemes\n\nThe novel explores themes of power, loyalty, and the moral complexities of leadership. It delves into the consequences of ambition and the struggle between personal honor and political necessity. The richly detailed world-building and intricate character development make A Game of Thrones a compelling and immersive read.\n\nKey Characters\n\n• Eddard \"Ned\" Stark: The honorable Lord of Winterfell and Warden of the North\n• Catelyn Stark: The devoted wife of Eddard Stark\n• Robert Baratheon: The King of the Seven Kingdoms\n• Cersei Lannister: The ambitious and cunning Queen of Westeros\n• Jaime Lannister: A skilled swordsman and member of the Kingsguard\n• Tyrion Lannister: The witty and resourceful dwarf\n• Daenerys Targaryen: An exiled princess of House Targaryen\n• Jon Snow: The bastard son of Eddard Stark\n• Sansa Stark: The eldest daughter of Eddard Stark\n• Arya Stark: The youngest daughter of Eddard Stark\n• Bran Stark: The second son of Eddard Stark",
                10591, 808, 832));

        fantasyBooks.add(new Book1("The Way of Kings", "Brandon Sanderson", R.drawable.book, "Read", "9780765326355",
                "Widely acclaimed for his work completing Robert Jordan's Wheel of Time saga, Brandon Sanderson now begins a grand cycle of his own, one every bit as ambitious and immersive.\n\nRoshar is a world of stone and storms. Uncanny tempests of incredible power sweep across the rocky terrain so frequently that they have shaped ecology and civilization alike. Animals hide in shells, trees pull in branches, and grass retracts into the soilless ground. Cities are built only where the topography offers shelter.\n\nIt has been centuries since the fall of the ten consecrated orders known as the Knights Radiant, but their Shardblades and Shardplate remain: mystical swords and suits of armor that transform ordinary men into near-invincible warriors. Men trade kingdoms for Shardblades. Wars are fought for them, and won by them.\n\nOne such war rages on a ruined landscape called the Shattered Plains. There, Kaladin, who traded his medical apprenticeship for a spear, has been reduced to slavery. In a war that makes no sense, where ten armies fight separately against a single foe, he struggles to save his men and to fathom the leaders who consider them expendable.\n\nBrightlord Dalinar Kholin commands one of those other armies. Like his brother, the late king, he is fascinated by an ancient text called The Way of Kings. Troubled by overpowering visions of ancient times and the Knights Radiant, he has begun to doubt his own sanity.\n\nAcross the ocean, an untried young woman named Shallan seeks to train under the eminent scholar and notorious heretic Jasnah Kholin, Dalinar's niece. Though she genuinely loves learning, Shallan's motives are less than pure. As she plans a daring theft, her research for Jasnah hints at secrets of the Knights Radiant and the true cause of the war.\n\nThe result of more than ten years of planning, writing, and worldbuilding, The Way of Kings is but the opening movement of the Stormlight Archive, a bold masterpiece in the making.",
                745, 41, 229));

        fantasyBooks.add(new Book1("The Mark of Athena", "Rick Riordan", R.drawable.book, "Read", "9780545782814",
                "Annabeth is terrified. Just when she's about to be reunited with Percy—after six months of being apart, thanks to Hera—it looks like Camp Jupiter is preparing for war. As Annabeth and her friends Jason, Piper, and Leo fly in on the Argo II, she can't blame the Roman demigods for thinking the ship is a Greek weapon. With its steaming bronze dragon figurehead, Leo's fantastical creation doesn't appear friendly. Annabeth hopes that the sight of their praetor Jason on deck will reassure the Romans that the visitors from Camp Half-Blood are coming in peace.\n\nAnd that's only one of her worries. In her pocket, Annabeth carries a gift from her mother that came with an unnerving command: Follow the Mark of Athena. Avenge me. Annabeth already feels weighed down by the prophecy that will send seven demigods on a quest to find—and close—the Doors of Death. What more does Athena want from her?\n\nAnnabeth's biggest fear, though, is that Percy might have changed. What if he's now attached to Roman ways? Does he still need his old friends? As the daughter of the goddess of war and wisdom, Annabeth knows she was born to be a leader—but never again does she want to be without Seaweed Brain by her side.\n\nNarrated by four different demigods, The Mark of Athena is an unforgettable journey across land and sea to Rome, where important discoveries, surprising sacrifices, and unspeakable horrors await. Climb aboard the Argo II, if you dare. . .",
                764, 93, 252));

        fantasyBooks.add(new Book1("Hatching Magic", "Ann E. Downer", R.drawable.book, "Read", "0689870574",
                "When a thirteenth-century wizard confronts twenty-first century Boston while seeking his pet dragon, he is followed by a rival wizard and a very unhappy demon, but eleven-year-old Theodora Oglethorpe may hold the secret to setting everything right.",
                16, 1, 5));

        List<Book1> scifiBooks = new ArrayList<>();
        scifiBooks.add(new Book1("The Biology of Science Fiction Cinema", "Mark C. Glassy", R.drawable.book, "Read", "0786409983",
                "A breakdown of the various sciences - both plausible and outlandish - featured in popular sci-fi movies from the 20th century.",
                10, 2, 0));

        scifiBooks.add(new Book1("Escape to Witch Mountain", "Alexander Key", R.drawable.book, "Read", "9780671297107",
                "A sci-fi classic returns to print in its true, best, and original form! With renewed interest in Alexander Key's extraordinary 1968 novel, fans can dive into Escape to Witch Mountain as it was meant to be read. The powerful, thrilling story of Tony and Tia—twins joined by their paranormal gifts, on the run from evil forces that seek to suppress their forgotten pasts—is more gripping and relevant than ever.\n\nPraise for Escape to Witch Mountain:\n\n\"Action, mood, and characterization never falter in this superior science fiction novel...\" - Library Journal\n\n\"Fantasy, science fiction, mystery, adventure—the story is all of these, with enough suspense and thrills to keep young readers glued to its pages from first to last.\" - Book World\n\n\"Fascinating science fiction.\" - Elementary School Library Collection, Bro-Dart Foundation",
                20, 1, 8));

        scifiBooks.add(new Book1("The Black Hole", "Alan Dean Foster", R.drawable.book, "Read", "0345285387",
                "A journey that begins where everything ends...now a spectacular motion picture. Deep space...alien life...an epic voyage into a giant black hole!!",
                13, 0, 2));

        scifiBooks.add(new Book1("Enemy Territory", "Keith R. A. DeCandido", R.drawable.book, "Read", "1416500146",
                "The Elabrej Hegemony\n\nFor centuries, the Elabrej firmly believed that they were alone in the universe, and that no sentient life existed outside their home star system. But their beliefs are shattered when a controversial exploration vessel of their own making encounters – and fires upon – an alien ship. The aliens return fire and destroy them – then come to Elabrej to investigate...\n\nThe Klingon Empire\n\nWhile exploring the uncharted Kavrot Sector, the crew of the IKS Gorkon learn that their brother ship, the IKS Kravokh, was fired on by an alien vessel and subsequently destroyed it. After setting course to investigate this new people, the Kravokh disappears – but a massive alien fleet is gathering at their last known location. Captain Klag must determine what has happened to the Kravokh, and who this new foe of the empire is...\n\nAs two civilizations prepare for war, the secret agendas of both the Elabrej oligarchs and Klingon Imperial Intelligence may serve only to deepen the conflict – and Captain Klag may also face a mutiny.",
                2, 0, 3));

        scifiBooks.add(new Book1("Wolf Tower", "Tanith Lee", R.drawable.book, "Read", "0142300306",
                "All her life, Claidi has endured hardship in the House, where she must obey a spoiled princess. Then a golden stranger arrives, living proof of a world beyond the House walls. Claidi risks all to free the charming prisoner and accompanies him across the Waste toward his faraway home. It is a difficult yet marvelous journey, and all the while Claidi is at the side of a man she could come to love. That is, until they reach his home . . . and the Wolf Tower.",
                28, 1, 5));
        listCategory.add(new Category("Trending Books", trendingBooks));
        listCategory.add(new Category("Classic Books", classicBooks));
        listCategory.add(new Category("Fantasy Books", fantasyBooks));
        listCategory.add(new Category("Sci-Fi Books", scifiBooks));

        return listCategory;
    }


    public void openRightDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
        HeaderFragment headerFragment = (HeaderFragment)
                getSupportFragmentManager().findFragmentById(R.id.headerFragment);
        if (headerFragment != null) {
            headerFragment.hideSearchBoxIfNeeded();
        }
    }

    public void closeRightDrawer() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            HeaderFragment headerFragment = (HeaderFragment)
                    getSupportFragmentManager().findFragmentById(R.id.headerFragment);
            if (headerFragment != null) {
                View searchBox = headerFragment.getSearchBoxView();
                if (searchBox != null && searchBox.getVisibility() == View.VISIBLE) {
                    int[] loc = new int[2];
                    searchBox.getLocationOnScreen(loc);
                    float x = ev.getRawX();
                    float y = ev.getRawY();

                    android.graphics.Rect rect = new android.graphics.Rect(
                            loc[0],
                            loc[1],
                            loc[0] + searchBox.getWidth(),
                            loc[1] + searchBox.getHeight()
                    );

                    if (!rect.contains((int) x, (int) y)) {
                        headerFragment.hideSearchBoxIfNeeded();
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    private void openWebPage(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
