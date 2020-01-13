# CurvedBottomNavigationView

## Intro
CurvedBottomNavigationView is Android Library fully written in Kotlin. Currently is has only one 
type of curve(UP) but I am planning to intergrate also DOWN curve
## Preview

<img src="https://imgur.com/ehmeRpR.png" height="480" style="float:left;"/>
<img src="https://imgur.com/j3FpW4t.png" height="480" style="float:left;"/>
                                                  
## Download
Gradle(Project)
``` Gradle
allprojects {
    repositories {
      ... 
      maven { url 'https://jitpack.io' }
    }
  }
```
Gradle(App)
``` Gradle
implementation 'com.github.kargoz:CurvedBottomNavigationView:1.1.1'
```
## Usage
Add CurvedBottomNavigationView to your layout 
``` XML
    <com.karen.curvedbottomnavigationview.CurvedBottomNavigationView
        android:id="@+id/curved_bnv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom" />
```
Fill CurvedBottomNavigationView with NavigationItems
``` Kotlin
curved_bnv.fill(
            true, NavigationItem("Text 1", R.drawable.drawable1),
            NavigationItem("Text 2", R.drawable.drawable2),
            NavigationItem("Text 3", R.drawable.drawable3),
            NavigationItem("Text 4", R.drawable.drawable4)
            )
```
Handle onClickListener
``` Kotlin
curved_bnv.setOnItemClickListener(object :OnItemClickedListener{
            override fun onItemClicked(item: NavigationItem) {
                /** Handle Item Clicked*/
            }

            override fun onItemReClicked(item: NavigationItem) {
                /** Handle Item ReClicked*/
            }

            override fun onCenterItemClicked() {
                /** Handle CenterItem Clicked*/
            }

            override fun onCenterItemReClicked() {
                /** Handle CenterItem ReClicked*/
            }
        })
```
