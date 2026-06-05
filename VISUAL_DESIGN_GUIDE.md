# Visual Design Guide - UI Modernization

## 🎨 Design Philosophy

### Prinsip Desain
1. **Clean & Minimal** - Fokus pada konten, hindari clutter
2. **Consistent** - Gunakan design system yang konsisten
3. **Responsive** - Adaptif terhadap berbagai screen size
4. **Accessible** - Mudah digunakan untuk semua user
5. **Professional** - Tampilan yang kredibel dan terpercaya

## 📐 Layout Structure

### Desktop Layout (>1024px)
```
┌─────────────────────────────────────────────────────────┐
│  Sidebar (260px)  │         Content Area                │
│                   │                                      │
│  ┌─────────────┐  │  ┌────────────────────────────────┐ │
│  │    Logo     │  │  │         Header                 │ │
│  └─────────────┘  │  └────────────────────────────────┘ │
│                   │                                      │
│  ┌─────────────┐  │  ┌──────────┐  ┌──────────┐        │
│  │ User Info   │  │  │  Card 1  │  │  Card 2  │        │
│  └─────────────┘  │  └──────────┘  └──────────┘        │
│                   │                                      │
│  ┌─────────────┐  │  ┌──────────┐  ┌──────────┐        │
│  │ Dashboard   │  │  │  Card 3  │  │  Card 4  │        │
│  ├─────────────┤  │  └──────────┘  └──────────┘        │
│  │ Buku        │  │                                      │
│  ├─────────────┤  │  ┌────────────────────────────────┐ │
│  │ Kategori    │  │  │         Table / Content        │ │
│  ├─────────────┤  │  │                                │ │
│  │ Anggota     │  │  │                                │ │
│  ├─────────────┤  │  └────────────────────────────────┘ │
│  │ Peminjaman  │  │                                      │
│  └─────────────┘  │                                      │
└─────────────────────────────────────────────────────────┘
```

### Tablet Layout (768-1024px)
```
┌───────────────────────────────────────────────┐
│  Sidebar  │         Content Area              │
│  (200px)  │                                    │
│           │  ┌──────────────────────────────┐ │
│  Logo     │  │         Header               │ │
│           │  └──────────────────────────────┘ │
│  User     │                                    │
│           │  ┌────────┐  ┌────────┐          │
│  Menu 1   │  │ Card 1 │  │ Card 2 │          │
│  Menu 2   │  └────────┘  └────────┘          │
│  Menu 3   │                                    │
│           │  ┌──────────────────────────────┐ │
│           │  │         Content              │ │
│           │  └──────────────────────────────┘ │
└───────────────────────────────────────────────┘
```

### Mobile Layout (<768px)
```
┌─────────────────────────┐
│  ☰  Header              │
├─────────────────────────┤
│                         │
│  ┌───────────────────┐  │
│  │      Card 1       │  │
│  └───────────────────┘  │
│                         │
│  ┌───────────────────┐  │
│  │      Card 2       │  │
│  └───────────────────┘  │
│                         │
│  ┌───────────────────┐  │
│  │      Card 3       │  │
│  └───────────────────┘  │
│                         │
│  ┌───────────────────┐  │
│  │     Content       │  │
│  │                   │  │
│  └───────────────────┘  │
│                         │
└─────────────────────────┘
```

## 🎨 Component Designs

### 1. Login Form

#### Desktop View
```
┌────────────────────────────────────────────────────────────┐
│                                                            │
│  ┌──────────────────────┐  ┌──────────────────────────┐  │
│  │                      │  │                          │  │
│  │   [Gradient BG]      │  │   Selamat Datang        │  │
│  │                      │  │                          │  │
│  │       📚             │  │   Username               │  │
│  │   PERPUSTAKAAN       │  │   ┌──────────────────┐  │  │
│  │                      │  │   │                  │  │  │
│  │   ✓ Manajemen Buku   │  │   └──────────────────┘  │  │
│  │   ✓ Data Anggota     │  │                          │  │
│  │   ✓ Peminjaman       │  │   Password               │  │
│  │   ✓ Laporan          │  │   ┌──────────────────┐  │  │
│  │                      │  │   │                  │  │  │
│  │                      │  │   └──────────────────┘  │  │
│  │                      │  │                          │  │
│  │                      │  │   ☐ Tampilkan Password  │  │
│  │                      │  │                          │  │
│  │                      │  │   ┌──────────────────┐  │  │
│  │                      │  │   │     MASUK        │  │  │
│  │                      │  │   └──────────────────┘  │  │
│  │                      │  │                          │  │
│  │                      │  │   ℹ️ Akun Demo:          │  │
│  │                      │  │   demo / demo123        │  │
│  │                      │  │                          │  │
│  └──────────────────────┘  └──────────────────────────┘  │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### 2. Dashboard Welcome Panel

```
┌────────────────────────────────────────────────────────────┐
│  Selamat Datang, Admin! 👋                                 │
│  Jumat, 22 Mei 2026 • 14:30:45                            │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │ 📚           │  │ 👥           │  │ 📋           │    │
│  │ Total Buku   │  │ Anggota      │  │ Dipinjam     │    │
│  │              │  │ Aktif        │  │              │    │
│  │    1,234     │  │     567      │  │      89      │    │
│  └──────────────┘  └──────────────┘  └──────────────┘    │
│                                                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │ ⚠️           │  │ 📊           │  │ 🎯           │    │
│  │ Terlambat    │  │ Kategori     │  │ Tersedia     │    │
│  │              │  │              │  │              │    │
│  │      12      │  │      45      │  │   1,145      │    │
│  └──────────────┘  └──────────────┘  └──────────────┘    │
│                                                            │
│  ┌────────────────────────────────────────────────────┐   │
│  │  ℹ️ Cara Penggunaan:                                │   │
│  │                                                     │   │
│  │  • Pilih menu di sidebar untuk navigasi            │   │
│  │  • Gunakan tombol Tambah, Edit, Hapus              │   │
│  │  • Gunakan kolom Cari untuk mencari data           │   │
│  └────────────────────────────────────────────────────┘   │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### 3. Form Buku (Modern)

```
┌────────────────────────────────────────────────────────────┐
│  📖 Manajemen Buku                                         │
│  Kelola koleksi buku perpustakaan                          │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  ┌──────────────┐  ┌────────────────────────────────────┐ │
│  │ Form Data    │  │  🔍 [Search...]    Filter: [All]   │ │
│  │ Buku         │  ├────────────────────────────────────┤ │
│  ├──────────────┤  │                                    │ │
│  │              │  │  No │ Judul │ Penulis │ Stok      │ │
│  │ Judul Buku   │  │  ───┼───────┼─────────┼──────     │ │
│  │ [________]   │  │   1 │ ...   │ ...     │ 10        │ │
│  │              │  │   2 │ ...   │ ...     │ 5         │ │
│  │ Penulis      │  │   3 │ ...   │ ...     │ 2  ⚠️     │ │
│  │ [________]   │  │   4 │ ...   │ ...     │ 0  ❌     │ │
│  │              │  │                                    │ │
│  │ Penerbit     │  │                                    │ │
│  │ [________]   │  │                                    │ │
│  │              │  │                                    │ │
│  │ Tahun  Stok  │  │                                    │ │
│  │ [___] [___]  │  │                                    │ │
│  │              │  │                                    │ │
│  │ Kategori     │  │                                    │ │
│  │ [▼ Pilih]    │  │                                    │ │
│  │              │  │                                    │ │
│  │ [+ Tambah]   │  │                                    │ │
│  │ [✏️ Update]   │  │                                    │ │
│  │ [🗑️ Hapus]    │  │                                    │ │
│  │ [🔄 Bersih]   │  │                                    │ │
│  │              │  │                                    │ │
│  │ 💡 Klik baris│  │  Total: 1,234 buku                │ │
│  │ untuk edit   │  │                                    │ │
│  └──────────────┘  └────────────────────────────────────┘ │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### 4. Stat Card Component

```
┌────────────────────────────┐
│  ┌────┐                    │
│  │ 📚 │  Total Buku        │
│  └────┘                    │
│         1,234              │
│                            │
│  [Accent Bar]              │
└────────────────────────────┘

Visual Details:
- White background
- Subtle shadow (0 4px 6px rgba(0,0,0,0.07))
- Rounded corners (12px)
- Left accent bar (4px, colored)
- Icon in colored circle (48px)
- Title: 13px, Gray 600
- Value: 28px Bold, Gray 900
- Padding: 16px
- Hover: Slight elevation increase
```

### 5. Modern Button States

```
Normal State:
┌──────────────┐
│    SUBMIT    │  Background: Primary (#3B82F6)
└──────────────┘  Text: White, Bold 13px
                  Border Radius: 8px
                  Padding: 12px 24px

Hover State:
┌──────────────┐
│    SUBMIT    │  Background: Primary Dark (#2563EB)
└──────────────┘  Cursor: Pointer
                  Subtle shadow

Pressed State:
┌──────────────┐
│    SUBMIT    │  Background: Darker
└──────────────┘  Slight scale down (98%)

Disabled State:
┌──────────────┐
│    SUBMIT    │  Background: Gray 300
└──────────────┘  Text: Gray 500
                  Cursor: Not-allowed
```

### 6. Modern Text Field States

```
Normal State:
┌────────────────────────────┐
│  Enter username...         │  Background: White
└────────────────────────────┘  Border: 1px Gray 300
                                Border Radius: 8px
                                Padding: 12px 16px

Focus State:
┌────────────────────────────┐
│  Enter username...│        │  Border: 2px Primary
└────────────────────────────┘  Glow effect

Filled State:
┌────────────────────────────┐
│  john.doe                  │  Text: Gray 900
└────────────────────────────┘  

Error State:
┌────────────────────────────┐
│  Enter username...         │  Border: 2px Danger
└────────────────────────────┘  Error message below
  ⚠️ Username is required
```

### 7. Table Design

```
┌────────────────────────────────────────────────────────────┐
│  No │ Judul Buku        │ Penulis      │ Stok │ Status    │
├─────┼───────────────────┼──────────────┼──────┼───────────┤
│  1  │ Pemrograman Java  │ John Doe     │  10  │ ✓ Tersedia│
├─────┼───────────────────┼──────────────┼──────┼───────────┤
│  2  │ Database MySQL    │ Jane Smith   │   5  │ ✓ Tersedia│
├─────┼───────────────────┼──────────────┼──────┼───────────┤
│  3  │ Web Development   │ Bob Johnson  │   2  │ ⚠️ Sedikit │
├─────┼───────────────────┼──────────────┼──────┼───────────┤
│  4  │ Mobile Apps       │ Alice Brown  │   0  │ ❌ Habis   │
└─────┴───────────────────┴──────────────┴──────┴───────────┘

Visual Details:
- Header: Bold 13px, Gray 900, 44px height
- Row: 40px height, alternating background
- Even rows: White
- Odd rows: Gray 50
- Selection: Primary with 30% opacity
- Borders: Horizontal only, Gray 200
- Cell padding: 8px 12px
- Hover: Slight background change
```

## 🎨 Color Usage Guidelines

### Primary (Blue)
- **Use for:** Main actions, links, active states
- **Don't use for:** Errors, warnings, destructive actions

### Success (Green)
- **Use for:** Success messages, positive actions, available status
- **Don't use for:** Errors, warnings

### Warning (Orange)
- **Use for:** Warnings, caution messages, low stock
- **Don't use for:** Errors, success messages

### Danger (Red)
- **Use for:** Errors, destructive actions, critical warnings
- **Don't use for:** Success, normal actions

### Neutral (Gray)
- **Use for:** Text, borders, backgrounds, disabled states
- **Don't use for:** Primary actions

## 📏 Spacing System

```
Component Spacing:
- Between cards: 16px
- Between sections: 24px
- Page padding: 24px
- Card padding: 16px
- Button padding: 12px 24px
- Input padding: 12px 16px

Vertical Rhythm:
- Heading to content: 12px
- Content to content: 16px
- Section to section: 24px
- Major sections: 32px
```

## 🔤 Typography Scale

```
Display:    48px Bold    (Hero text)
H1:         32px Bold    (Page titles)
H2:         24px Bold    (Section titles)
H3:         18px Bold    (Subsection titles)
Body Large: 15px Regular (Important text)
Body:       14px Regular (Default text)
Body Small: 13px Regular (Secondary text)
Caption:    11px Regular (Helper text)
```

## 🎯 Interactive States

### Hover
- Background: Slightly darker/lighter
- Cursor: Pointer
- Transition: 200ms ease

### Active/Pressed
- Background: Darker
- Scale: 98%
- Transition: 100ms ease

### Focus
- Border: 2px Primary
- Outline: None (custom focus ring)
- Glow: Subtle shadow

### Disabled
- Background: Gray 300
- Text: Gray 500
- Cursor: Not-allowed
- Opacity: 0.6

## 📱 Responsive Breakpoints

```
Mobile:     < 768px
  - Single column layout
  - Stacked components
  - Full-width buttons
  - Larger touch targets (44px min)
  - Simplified navigation

Tablet:     768px - 1024px
  - Two column layout
  - Compact sidebar
  - Medium spacing
  - Touch-friendly

Desktop:    1024px - 1440px
  - Multi-column layout
  - Full sidebar
  - Standard spacing
  - Mouse-optimized

Large:      > 1440px
  - Wide layout
  - Extra spacing
  - Larger fonts
  - More content visible
```

## 🎨 Animation Guidelines

### Duration
- Micro: 100ms (hover, active)
- Short: 200ms (fade, slide)
- Medium: 300ms (panel transitions)
- Long: 400ms (page transitions)

### Easing
- Ease-in-out: Default
- Ease-out: Entrances
- Ease-in: Exits
- Linear: Progress indicators

### Types
- Fade: Opacity changes
- Slide: Position changes
- Scale: Size changes
- Rotate: Rotation changes

## 💡 Best Practices

### Do's ✅
- Use consistent spacing
- Maintain color contrast (4.5:1 minimum)
- Provide visual feedback
- Use smooth transitions
- Test on multiple screen sizes
- Keep it simple and clean
- Use icons consistently
- Provide loading states

### Don'ts ❌
- Don't use too many colors
- Don't use inconsistent spacing
- Don't make buttons too small
- Don't use too many animations
- Don't ignore accessibility
- Don't use low contrast
- Don't overcomplicate layouts
- Don't forget error states

## 🎯 Accessibility

### Color Contrast
- Normal text: 4.5:1 minimum
- Large text: 3:1 minimum
- UI components: 3:1 minimum

### Touch Targets
- Minimum: 44x44px
- Recommended: 48x48px
- Spacing: 8px minimum

### Keyboard Navigation
- Tab order: Logical
- Focus indicators: Visible
- Shortcuts: Documented

### Screen Readers
- Alt text: Descriptive
- Labels: Clear
- ARIA: When needed

---

**Design with purpose, build with care! 🎨**
